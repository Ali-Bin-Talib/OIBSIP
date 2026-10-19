# Sentiment Analysis on Movie Reviews

A machine learning project that classifies movie reviews as positive or negative using NLP.

## What this project does
- Loads 2000 movie reviews (NLTK movie_reviews) and checks the class distribution
- Text preprocessing: lowercase, punctuation removal, tokenisation, stopword removal, lemmatization
- TF-IDF feature extraction
- 80/20 train/test split
- Trains Naive Bayes and Logistic Regression
- Evaluates with accuracy, precision, recall, F1-score and confusion matrices
- Sentiment distribution bar chart and word clouds for each class
- Error analysis of misclassified reviews
- Conclusion on the best model and real-world use

## Dataset
NLTK movie_reviews corpus. It contains only positive and negative reviews, so this is a binary classification task.

## Tech Stack
Python, pandas, NLTK, scikit-learn, matplotlib, seaborn, WordCloud, Jupyter Notebook (Google Colab)

## How to Run
Open Sentiment_Analysis.ipynb in Google Colab and run all cells. The dataset is downloaded automatically with NLTK.
