import pandas as pd
from LocationHelper import *

df_healthcare_centres = pd.read_csv("healthcare_centres_dataset.csv")
# print(df_healthcare_centres.head())

present_state = get_location()
# print(present_state)

present_state_data = df_healthcare_centres.loc[df_healthcare_centres['States/Union Territory'] == present_state]
# print(present_state_data)

# print(present_state_data['Total Number of HealthCare Units'].min())
min_val = present_state_data['Total Number of HealthCare Units'].min()
min_hc_units_row = df_healthcare_centres.loc[df_healthcare_centres['Total Number of HealthCare Units'] == min_val]

print(min_hc_units_row['Name of the District'])


