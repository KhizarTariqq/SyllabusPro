from flask import Flask, request, jsonify
from python_backend.itemExtractor import extract_items_from_file

app = Flask(__name__)


@app.route("/parsePDF", methods=["POST"])
def parse_pdf():
    file = request.files["file"]

    if file.filename.lower().endswith(".pdf"):
        items = extract_items_from_file(file)
        return jsonify(items), 200
    
    else:
        return jsonify({"error": "File must be a PDF"}), 400