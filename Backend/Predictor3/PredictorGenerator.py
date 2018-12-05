import csv
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.externals import joblib
from sklearn.preprocessing import OneHotEncoder
from sklearn.neighbors import KNeighborsClassifier
import pandas as pd
from sklearn.tree import DecisionTreeClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn.naive_bayes import GaussianNB
from sklearn.svm import SVC


class PredictGenerate():
    def __init__(self, algo):
        self.years = [2011, 2012, 2013, 2014, 2015, 2016, 2017]
        self.sc_X = StandardScaler()
        self.onehotencoder = OneHotEncoder(categorical_features=[0, 1])
        self.event_dict = {1: 'poverty', 2: 'health_care', 3: 'education', 4: 'donations'}
        self.algo = algo
        self.model = self.get_basic_model(self.algo)

    def get_basic_model(self):
        dataset = pd.read_csv("random_dataset_2010.csv")
        X = dataset.iloc[:, [1, 5]].values
        y = dataset.iloc[:, 7].values
        onehotencoder = OneHotEncoder(categorical_features=[0, 1])
        X = onehotencoder.fit_transform(X).toarray()
        X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.25, random_state=0)
        sc_X = StandardScaler()
        X_train = sc_X.fit_transform(X_train)
        model = self.fit_model(X_train, y_train)
        filename = self.algo+"_model_trained_on_2010.pkl"
        joblib.dump(model, filename)
        return model

    def set_up_data(self):
        for year in self.years:
            filename = "dataset_" + str(year) + ".csv"
            df = pd.read_csv(filename)
            X_data = df.iloc[:, [1, 5]].values
            self.make_prediction_data(df, X_data, year)

    def make_prediction_data(self, df, X_data, year):
        k = 1000
        for row in range(0, 10000, k):
            x_write = []
            X = X_data[row:row + k, :]
            for i in xrange(k):
                x_write.append(list(df.iloc[i, :].values))

            X_encoded = self.encode_data(X)
            X_test = self.get_scaled_test_data(X_encoded)
            self.predict_data(X_test, x_write, k, year)
            self.make_new_model(year)

    def predict_data(self, x_test, x_file, k, year):
        y_pred = self.model.predict(x_test)
        for i in range(k):
            event = self.event_dict[y_pred[i]]
            x_file[i].append(event)
            x_file[i].append(y_pred[i])
            self.write_to_file(x_file[i], year)

    def write_to_file(self, row, year):
        filename = self.algo+"_dataset.csv"
        with open(filename, 'a') as csvFile:
            writer = csv.writer(csvFile)
            writer.writerow(row)
        csvFile.close()

    def make_new_model(self, year):
        filename = self.algo + "_dataset.csv"
        dataset = pd.read_csv(filename)
        X = dataset.iloc[:, [1, 5]].values
        y = dataset.iloc[:, 7].values
        X = self.encode_data(X)
        X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.25, random_state=0)
        X_train, X_test = self.get_scaled_data(X_train, X_test)
        self.model = self.fit_model(X_train, y_train)
        filename = self.algo+"_model.pkl"
        joblib.dump(self.model, filename)
        score = self.model.score(X_test, y_test)
        print("Test score for year {} : {}%".format(year, 100 * score))

    def fit_model(self, X_train, y_train):
        model = None
        if self.algo == "knn":
            model = KNeighborsClassifier(n_neighbors=8, metric='minkowski', p=2)
            model.fit(X_train, y_train)
        elif self.algo == "svm":
            model = SVC(kernel='linear', random_state=0)
            model.fit(X_train, y_train)

        elif self.algo == "decision_tree":
            model = DecisionTreeClassifier(criterion='entropy', random_state=0)
            model.fit(X_train, y_train)

        elif self.algo == "random_forest":
            model = RandomForestClassifier(n_estimators=10, criterion='entropy', random_state=0)
            model.fit(X_train, y_train)

        elif self.algo == "nb":
            model = GaussianNB()
            model.fit(X_train, y_train)

        return model

    def get_scaled_test_data(self, X_test):
        X_test = self.sc_X.fit_transform(X_test)
        return X_test

    def get_scaled_data(self, X_train, X_test):
        X_train = self.sc_X.fit_transform(X_train)
        X_test = self.sc_X.fit_transform(X_test)
        return X_train, X_test

    def encode_data(self, X):
        X = self.onehotencoder.fit_transform(X).toarray()
        return X


print "SVM : ********"
PredictGenerate("svm").set_up_data()

print "Random Forest : ********"
PredictGenerate("random_forest").set_up_data()

print "Decision Tree : ********"
PredictGenerate("decision_tree").set_up_data()

print "Naive Bayes : ********"
PredictGenerate("nb").set_up_data()
