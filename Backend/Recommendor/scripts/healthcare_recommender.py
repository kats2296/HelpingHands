import pandas as pd
from Recommendor.Helper.LocationHelper import *


def get_health_districts():
    df_healthcare_centres = pd.read_csv("healthcare_centres_dataset.csv")

    present_state = get_location()

    present_state_data = df_healthcare_centres.loc[df_healthcare_centres['States/Union Territory'] == present_state]

    min_list = present_state_data.nsmallest(10, 'Total Number of HealthCare Units')

    districts = min_list['Name of the District'].values
    return districts
    #
    # for d in districts:
    #     print(get_lat_lng(d))
