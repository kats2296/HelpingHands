import pandas as pd
import random
import csv


class DummyDataBuilderEncoded:

    def __init__(self):
        self.years = [2013, 2014, 2015, 2016, 2017]
        self.months = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]

    def generate_data(self):
            for i in xrange(10000):
                year = 2014
                self.organise_data_from_csv(year)

    def organise_data_from_csv(self, year):
        states_districts = pd.read_csv("important_districts.csv").iloc[:, 1: 5]
        random_row = states_districts.sample(1)
        state = ''.join(random_row['STATNAME'].values)
        state_code = int(random_row['STATCD'])
        district = ''.join(random_row['DISTNAME'].values)
        district_code = int(random_row['DISTCD'])

        month = random.choice(self.months)

        self.write_data_to_csv(year, month, state, state_code, district, district_code)

    @staticmethod
    def write_data_to_csv(year, month, state, state_code, district, district_code):

        row = [year, month, state, state_code, district, district_code]

        with open('dataset_2014.csv', 'a') as csvFile:
            writer = csv.writer(csvFile)
            writer.writerow(row)

        csvFile.close()


DummyDataBuilderEncoded().generate_data()






