from flask import Flask, request, jsonify
from python_backend.item_with_details_extractor import extract_items_with_details
from .syllabus_item import SyllabusItem
app = Flask(__name__)


@app.route("/parsePDF", methods=["POST"])
def parse_pdf():
    file = request.files["file"]

    if file.filename.lower().endswith(".pdf"):
        items = extract_items_with_details(file)

        item_dicts = [SyllabusItem.to_dict(item) for item in items]
        return jsonify(item_dicts), 200
    
    else:
        return jsonify({"error": "File must be a PDF"}), 400