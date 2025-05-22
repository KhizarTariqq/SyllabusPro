from itemExtractor import extract_items

with open("pdfs/1 Syllabus 20241_PHY100H5S_LEC0101.pdf", "rb") as f:  # open as binary
    print(extract_items(f))
