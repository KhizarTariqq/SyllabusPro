from .item_extractor import extract_items_from_file
from dateutil.parser import parse
import dateutil
from flask_api.syllabus_item import SyllabusItem, ItemType
import spacy
import os

model_path = os.path.join(os.path.dirname(__file__), "extract_details_model", "model", "model-best")
nlp = spacy.load(model_path)

def extract_items_with_details(file):
    """
    Run the extract_items_model on the file to extract all syllabus items from it.
    Then run the extract_details_model on each of those items to extract that
    syllabus item's details, like its type, due_date, weight etc. Return a list
    of syllabus item objects which contain this information about each item.
    """

    extracted_items = extract_items_from_file(file)
    item_objects = []

    for item in extracted_items:
        doc = nlp(item)

        type = None
        description = None
        due_date = None
        weight = None

        for ent in doc.ents:
            match ent.label_:
                # "TYPE","DESCRIPTION","WEIGHT","DATE"
                case "TYPE":
                    type = ItemType.from_string(ent.text)

                case "DESCRIPTION":
                    description = ent.text
                
                case "DATE":
                    # TODO create custom date type that can either be a date or "ongoing/tba"
                    if ent.text.strip().lower() not in {"ongoing", "on-going", "tba", "tbd"}:
                        due_date = parse(ent.text).date()

                case "WEIGHT":
                    # Try to treat weight label as a date, in case the second ner accidentally
                    # tagged a date as a weight
                    try:
                        parse(ent.text)
                        due_date = parse(ent.text).date()
                    except dateutil.parser._parser.ParserError:
                        try:
                            weight = float(ent.text[:-1])
                        except ValueError:
                            weight = None

        syllabus_item = SyllabusItem(type=type, description=description, weight=weight, due_date=due_date)
        item_objects.append(syllabus_item)

    return item_objects