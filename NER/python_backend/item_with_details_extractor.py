from .item_extractor import extract_items_from_file
from dateutil.parser import parse
import dateutil
from flask_api.syllabus_item import SyllabusItem, ItemType
import spacy
import os

model_path = os.path.join(os.path.dirname(__file__), "extract_details_model", "model", "model-best")
nlp = spacy.load(model_path)

exam_descriptions = {"final exam", "exam", "final", "final examination"}

def extract_items_with_details(file):
    """
    Run the extract_items_model on the file to extract all syllabus items from it.
    Then run the extract_details_model on each of those items to extract that
    syllabus item's details, like its type, due_date, weight etc. Return a list
    of syllabus item objects which contain this information about each item.
    """

    extracted_items = extract_items_from_file(file)
    print(extracted_items)
    item_objects = []
    found_exam = False
    processed_exam = False

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
                    print(f"type: {type}, type is {type.__class__}")
                    print(f"Is type == ItemType.EXAM? {type == ItemType.EXAM}")
                    print(f"ItemType.EXAM is {ItemType.EXAM}")
                    if (type == ItemType.EXAM):
                        print(f"type is exam, found")
                        found_exam = True

                case "DESCRIPTION":
                    description = ent.text
                    if description.strip().lower() in {"exam", "final exam", "final", "final examination"}:
                        found_exam = True
                        print("description contains exam, found")
                
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
        print(f"\nItem information: type: {type}, description: {description}, due date: {due_date}, weight: {weight}, found_exam: {found_exam}, proccesed_exam: {processed_exam}")
        # Add the newly created item unless:
        #   # 1. It has no weight or due date (which means the first NER picked up something
        #        that isn't a Syllabus Item). 
        #   # 2. The item is an "exam" but we already processed an exam (In CS syllabi there is
        #        a 40% rule for exams which sometimes is picked up as a syllabus item)
        if not ((weight is None and due_date is None) or (processed_exam and description.strip().lower() in exam_descriptions)
                or (processed_exam and type == ItemType.EXAM)):
            print(f"added: {description}")
            syllabus_item = SyllabusItem(type=type, description=description, weight=weight, due_date=due_date)
            item_objects.append(syllabus_item)

            processed_exam = found_exam

    return item_objects