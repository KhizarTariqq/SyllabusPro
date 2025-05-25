import requests

url = "http://127.0.0.1:5000/parsePDF"  # Flask server address
file_path = "../python_backend/extractItemsModel/data/testing_pdfs/20249_CSC420H5F_LEC0101.pdf"

with open(file_path, "rb") as f:
    files = {"file": f}  # "file" is the key your Flask server expects
    response = requests.post(url, files=files)

print("Server response:", response.text)