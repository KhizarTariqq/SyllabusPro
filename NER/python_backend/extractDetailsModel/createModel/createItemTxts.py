import os
from python_backend.itemExtractor import extract_items_from_text

# This file creates the txt files for each syllabus item within each syllabus that was
# used to train the extractItemsModel, which extracts all of the Syllabus Items from a
# parsed syllabus txt

# Directory of the current script
base_dir = os.path.dirname(os.path.abspath(__file__))

# Input directories
# EIM = ExtractItemsModel
# The training txt directory of the EIM
EIM_train_txt_dir = os.path.abspath(os.path.join(base_dir, "..", "..", "extractItemsModel", "data", "training_txts"))
EIM_validation_txt_dir = os.path.abspath(os.path.join(base_dir, "..", "..", "extractItemsModel", "data", "validation_txts"))
EIM_testing_txt_dir = os.path.abspath(os.path.join(base_dir, "..", "..", "extractItemsModel", "data", "testing_txts"))

# Output directories
# EDM = ExtractDetailsModel
# The training txt directory of the EDM
EDM_train_txt_dir = os.path.abspath(os.path.join(base_dir, "..", "data", "training_txts"))
EDM_validation_txt_dir = os.path.abspath(os.path.join(base_dir, "..", "data", "validation_txts"))
EDM_testing_txt_dir = os.path.abspath(os.path.join(base_dir, "..", "data", "testing_txts"))

def createItemTxts(EIMDirectory, EDMDirectory):
    """
    For each txt datapoint (parsed pdf) in EIM's data directory, run EIM on it to extract
    the syllabus items, and save each syllabus item into its own txt file for EDM training
    """
    for filename in os.listdir(EIMDirectory):
        print(filename)
        if filename.endswith('.txt'):
            with open(os.path.join(EIMDirectory, filename), "r", encoding="utf-8") as f:
                text = f.read()
            items_in_file = extract_items_from_text(text)

            counter = 1
            for item in items_in_file:
                new_file_name = filename[:-4] + f"-item-{counter}"
                new_file_path = os.path.join(EDMDirectory, new_file_name)
                counter += 1

                with open(new_file_path, "w") as f:
                    f.write(item)

# Create training txts
createItemTxts(EIM_train_txt_dir, EDM_train_txt_dir)

# Create validation txts
createItemTxts(EIM_validation_txt_dir, EDM_validation_txt_dir)

# Create testing txts
createItemTxts(EIM_testing_txt_dir, EDM_testing_txt_dir)