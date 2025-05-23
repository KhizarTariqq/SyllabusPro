import os
from processPDF import process_pdf

pdf = os.path.join(os.path.dirname(__file__), '..', 'extractItemsModel', 'data' ,'training_pdfs', '20249_CHM211H5F_LEC0101.pdf')
newFileName = os.path.join(os.path.dirname(__file__), '..', 'extractItemsModel', 'data', 'training_txts', '20249_CHM211H5F_LEC0101.txt')
with open(newFileName, "w") as f:
            f.write(process_pdf(pdf))


            