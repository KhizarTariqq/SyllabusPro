import os
from processPDF import process_pdf

# Process each pdf in the pdfs directory (contains both training and validation data)
# and each pdf in the test_pdf_dir directory (contains test data)

train_pdf_dir = os.path.join(os.path.dirname(__file__), 'data/training_pdfs')
validation_pdf_dir = os.path.join(os.path.dirname(__file__), 'data/validation_pdfs')
test_pdf_dir = os.path.join(os.path.dirname(__file__), 'data/testing_pdfs')

# Parse text for training data points
for filename in os.listdir(train_pdf_dir):
    if filename.endswith('.pdf'):
        newFileName = "training_txts/" + filename[:-4] + ".txt"
        with open(newFileName, "w") as f:
            f.write(process_pdf("training_pdfs/" + filename))

# Parse text for validation data points
for filename in os.listdir(validation_pdf_dir):
    if filename.endswith('.pdf'):
        newFileName = "validation_txts/" + filename[:-4] + ".txt"
        with open(newFileName, "w") as f:
            f.write(process_pdf("validation_pdfs/" + filename))

# Test data
for filename in os.listdir(test_pdf_dir):
    if filename.endswith('.pdf'):
        newFileName = "testing_txts/" + filename[:-4] + ".txt"
        with open(newFileName, "w") as f:
            f.write(process_pdf("testing_pdfs/" + filename))