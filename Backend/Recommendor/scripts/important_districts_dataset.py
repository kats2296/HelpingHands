import pandas as pd
import random
import csv


def write_data_to_csv(row):
    with open('important_districts.csv', 'a') as csvFile:
        writer = csv.writer(csvFile)
        writer.writerow(row)

    csvFile.close()


df_poverty = pd.read_csv("poverty_dataset.csv").iloc[:, 0: 9]

all_states = df_poverty['STATNAME']

for state in all_states.unique():
    present_state_data = df_poverty.loc[df_poverty['STATNAME'] == state.upper()]
    min_list = present_state_data.nsmallest(3, 'P_URB_POP')
    print(min_list.values)
    for row in min_list.values:
        write_data_to_csv(row)
