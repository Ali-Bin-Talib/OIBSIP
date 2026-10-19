# Cleaning Data (Titanic)

A data cleaning project that turns the raw Titanic dataset into an analysis-ready dataset, with every decision documented.

## What this project does
- Data quality report: nulls per column, duplicate rows, data types, value ranges
- Missing data handling with a justified strategy for each column
- Duplicate detection and removal
- Standardisation of text values (casing, whitespace)
- Outlier detection with the IQR method and a decision for each column
- Data type correction
- Before vs after summary table
- Cleaned dataset saved as titanic_cleaned.csv

## Tech Stack
Python, pandas, numpy, seaborn, Jupyter Notebook (Google Colab)

## How to Run
Open Cleaning_Data.ipynb in Google Colab and run all cells. The Titanic data is loaded with seaborn, so no download is needed.
