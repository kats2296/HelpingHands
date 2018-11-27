import pandas as pd
import random
import csv


class DummyDataBuilder:

    def __init__(self):
        self.years = [2013, 2014, 2015, 2016, 2017]
        self.months = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12']
        self.events = ['poverty', 'health_care', 'education', 'donations']
        self.event_codes = ['1', '2', '3', '4']

    def generate_data(self):
        for year in self.years:
            for i in xrange(5):
                self.organise_data_from_csv(year)

    def organise_data_from_csv(self, year):
        states_districts = pd.read_csv("poverty_dataset.csv").iloc[:, 1: 5]
        random_row = states_districts.sample(1)
        state = ''.join(random_row['STATNAME'].values)
        state_code = "".join(map(str, random_row['STATCD']))
        district = ''.join(random_row['DISTNAME'].values)
        district_code = "".join(map(str, random_row['DISTCD']))

        month = random.choice(self.months)
        event = random.choice(self.events)
        event_code = random.choice(self.event_codes)

        self.write_data_to_csv(year, month, state, state_code, district, district_code, event, event_code)

    @staticmethod
    def write_data_to_csv(year, month, state, state_code, district, district_code, event, event_code):

        row = [year, month, state, state_code, district, district_code, event, event_code]

        with open('events_data_new.csv', 'a') as csvFile:
            writer = csv.writer(csvFile)
            writer.writerow(row)

        csvFile.close()


DummyDataBuilder().generate_data()






