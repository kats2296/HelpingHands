import pandas as pd
import random
import csv


class DummyDataBuilder:

    def __init__(self):
        self.years = [2013, 2014, 2015, 2016, 2017]
        self.months = ['january', 'february', 'march', 'april', 'may', 'june', 'july', 'august', 'september', 'october'
            , 'november', 'december']
        self.events = ['poverty', 'health_care', 'education', 'donations']

    def generate_data(self):
        for year in self.years:
            for i in xrange(50000):
                self.organise_data_from_csv(year)

    def organise_data_from_csv(self, year):
        states_districts = pd.read_csv("healthcare_centres_dataset.csv").iloc[:, 1: 3]
        random_row = states_districts.sample(1)
        state = ''.join(random_row['States/Union Territory'].values)
        district = ''.join(random_row['Name of the District'].values)
        month = random.choice(self.months)
        event = random.choice(self.events)

        self.write_data_to_csv(year, month, state, district, event)

    @staticmethod
    def write_data_to_csv(year, month, state, district, event):

        row = [year, month, state, district, event]

        with open('events_data.csv', 'a') as csvFile:
            writer = csv.writer(csvFile)
            writer.writerow(row)

        csvFile.close()


DummyDataBuilder().generate_data()






