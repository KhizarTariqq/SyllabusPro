from .item_with_details_extractor import extract_items_with_details
import os

# 20219_CSC108H5F_LEC9101
# 20229_CSC398H5F_LEC0101
# 20249_CSC420H5F_LEC0101

with open(os.path.join(os.path.dirname(__file__), "extract_items_model", "data", "testing_pdfs", "20219_CSC108H5F_LEC9101.pdf"), "rb") as f: 
    items = extract_items_with_details(f)

for item in items:
    print(item)

