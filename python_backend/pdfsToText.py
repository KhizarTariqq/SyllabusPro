import os
import pdfplumber
from collections import defaultdict

def extract_rows_from_page(page, y_tolerance=3):
    words = page.extract_words()
    rows = defaultdict(list)

    for word in words:
        if "text" not in word or "x0" not in word or "top" not in word:
            continue
        y_key = round(float(word["top"]) / y_tolerance) * y_tolerance
        rows[y_key].append(word)

    sorted_y = sorted(rows.keys())
    lines = []
    for y in sorted_y:
        line = sorted(rows[y], key=lambda w: w["x0"])
        line_text = ' '.join(w["text"] for w in line)
        lines.append(line_text)
    return lines

def process_pdf_manual_rows(pdf_path):
    all_text = []
    with pdfplumber.open(pdf_path) as pdf:
        for page in pdf.pages:
            print(f"Processing page {page.page_number}")
            lines = extract_rows_from_page(page)
            all_text.extend(lines)
    return '\n'.join(all_text)

pdf_dir = os.path.join(os.path.dirname(__file__), 'pdfs')

for filename in os.listdir(pdf_dir):
    if filename.endswith('.pdf'):
        newFileName = "txts/" + filename[:-4] + ".txt"
        with open(newFileName, "w") as f:
            f.write(process_pdf_manual_rows("pdfs/" + filename))
