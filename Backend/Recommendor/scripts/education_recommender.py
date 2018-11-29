import pandas as pd
from Recommendor.Helper.LocationHelper import *


def get_literacy_districts():
    df_literates = pd.read_csv("literacy_dataset.csv")

    present_state = get_location()

    state_data = df_literates.loc[df_literates['INDIA/STATE/UT/DISTRICT'] == present_state.upper()]

    state_code = state_data['STATE CODE'].values

    present_state_literates_data = df_literates.loc[df_literates['STATE CODE'] == state_code[0]]

    present_state_rural_data = present_state_literates_data.loc[present_state_literates_data['TRU'] == "Rural"]

    min_list = present_state_rural_data.nsmallest(10, 'PL_Persons')

    districts = min_list['INDIA/STATE/UT/DISTRICT'].values
    # print(districts)

    return districts
    # for d in districts:
    #     print(get_lat_lng(d))


get_literacy_districts()
