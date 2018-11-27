import pandas as pd
from LocationHelper import *

df_poverty = pd.read_csv("poverty_dataset.csv")

present_state = get_location()

present_state_data = df_poverty.loc[df_poverty['STATNAME'] == present_state.upper()]

min_list = present_state_data.nsmallest(3, 'P_URB_POP')
districts = min_list['DISTNAME'].values
print(districts)


