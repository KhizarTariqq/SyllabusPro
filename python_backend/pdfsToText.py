import os
from processPDF import process_pdf

pdf_dir = os.path.join(os.path.dirname(__file__), 'pdfs')
test_pdf_dir = os.path.join(os.path.dirname(__file__), 'testing_pdfs')

# Training and validation data points
for filename in os.listdir(pdf_dir):
    if filename.endswith('.pdf'):
        newFileName = "txts/" + filename[:-4] + ".txt"
        with open(newFileName, "w") as f:
            f.write(process_pdf("pdfs/" + filename))

# Test data
for filename in os.listdir(test_pdf_dir):
    if filename.endswith('.pdf'):
        newFileName = "testing_txts/" + filename[:-4] + ".txt"
        with open(newFileName, "w") as f:
            f.write(process_pdf("testing_pdfs/" + filename))