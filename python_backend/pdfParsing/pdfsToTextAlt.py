import os
import pdfplumber
from collections import defaultdict

"""
This alternative parsing method extracts text from tables
"""

TABLE_SETTINGS = {
    "vertical_strategy": "lines",          # only if there are no ruling lines
    "horizontal_strategy": "lines",
    "join_tolerance": 4,                  # tiny gaps in borders
    "text_x_tolerance": 0.5,                # fight random spaces
    "text_y_tolerance": 4,                # keep wrapped lines together
}

def extract_rows_from_page(page):
    rows = page.extract_table(
        TABLE_SETTINGS,  # from section 1
    )
    # rows is already grouped: join each cell’s lines with spaces
    return ['\t'.join(c.replace('\n', ' ') if c else '' for c in row)
            for row in rows or []]

def process_pdf_manual_rows(pdf_path):
    all_text = []
    with pdfplumber.open(pdf_path) as pdf:
        for page in pdf.pages:
            print(f"Processing page {page.page_number}")
            lines = extract_rows_from_page(page)
            all_text.extend(lines)
    return '\n'.join(all_text)

pdf_dir = os.path.join(os.path.dirname(__file__), 'pdfs')
test_pdf_dir = os.path.join(os.path.dirname(__file__), 'testing_pdfs')

for filename in os.listdir(pdf_dir):
    if filename.endswith('.pdf'):
        newFileName = "newMethodTest/" + filename[:-4] + ".txt"
        with open(newFileName, "w") as f:
            f.write(process_pdf_manual_rows("pdfs/" + filename))

newFileName = "tabula_txts/" + "output" + ".txt"
with open(newFileName, "w") as f:
    f.write(process_pdf_manual_rows("pdfs/LIN205 (WS23) - Course Syllabus.pdf"))

#for filename in os.listdir(test_pdf_dir):
#    if filename.endswith('.pdf'):
#        newFileName = "testing_txts/" + filename[:-4] + ".txt"
#        with open(newFileName, "w") as f:
#            f.write(process_pdf_manual_rows("testing_pdfs/" + filename))