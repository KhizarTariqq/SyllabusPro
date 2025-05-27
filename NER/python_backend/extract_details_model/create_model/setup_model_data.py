import training_data_strings as tStrings
import validation_data_strings as vStrings
import spacy
from spacy.tokens import DocBin
import os

nlp = spacy.blank("en")

# Paths to save spacy data to
script_dir = os.path.dirname(os.path.abspath(__file__))
parent_dir = os.path.abspath(os.path.join(script_dir, ".."))
train_save_path = os.path.join(parent_dir, "train.spacy")
validation_save_path = os.path.join(parent_dir, "dev.spacy")

training_data = [
    (tStrings.CHM110_1, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CHM110_2, [(0, 3, "TYPE"), (4, 37, "DESCRIPTION"), (70, 78, "DATE"), (79, 81, "WEIGHT")]),
    (tStrings.CHM110_3, [(0, 3, "TYPE"), (4, 33, "DESCRIPTION"), (66, 74, "DATE"), (75, 78, "WEIGHT")]),
    (tStrings.CHM110_4, [(0, 4, "TYPE"), (5, 34, "DESCRIPTION"), (67, 75, "DATE"), (76, 79, "WEIGHT")]),
    (tStrings.CHM110_5, [(0, 4, "TYPE"), (5, 30, "DESCRIPTION"), (63, 71, "DATE"), (72, 74, "WEIGHT")]),
    (tStrings.CHM110_8, [(0, 10, "TYPE"), (11, 21, "DESCRIPTION"), (32, 35, "DATE"), (36, 39, "WEIGHT")]),
    (tStrings.CHM211_1, [(0, 11, "DESCRIPTION"), (22, 25, "DATE"), (26, 29, "WEIGHT")]),
    (tStrings.CHM211_2, [(0, 9, "TYPE"), (10, 21, "DESCRIPTION"), (32, 35, "DATE"), (36, 39, "WEIGHT")]),
    (tStrings.CHM211_3, [(0, 3, "TYPE"), (4, 29, "DESCRIPTION"), (48, 56, "DATE"), (57, 60, "WEIGHT")]),
    (tStrings.CHM211_4, [(0, 3, "TYPE"), (4, 21, "DESCRIPTION"), (27, 35, "DATE"), (36, 39, "WEIGHT")]),
    (tStrings.CHM211_5, [(0, 10, "DESCRIPTION"), (42, 50, "DATE"), (51, 54, "WEIGHT")]),
    (tStrings.CSC209_1, [(0, 5, "TYPE"), (6, 25, "DESCRIPTION"), (26, 34, "DATE"), (35, 37, "WEIGHT")]),
    (tStrings.CSC209_4, [(0, 10, "TYPE"), (11, 47, "DESCRIPTION"), (48, 58, "DATE"), (59, 61, "WEIGHT")]),
    (tStrings.CSC209_7, [(0, 10, "TYPE"), (11, 36, "DESCRIPTION"), (37, 47, "WEIGHT"), (48, 50, "DATE")]),
    (tStrings.CSC209_8, [(0, 10, "DESCRIPTION"), (38, 41, "DATE"), (42, 45, "WEIGHT")]),
    (tStrings.CSC209_9, [(0, 5, "TYPE"), (6, 56, "DESCRIPTION"), (57, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC258_1, [(0, 3, "TYPE"), (4, 64, "DESCRIPTION"), (73, 81, "DATE"), (82, 85, "WEIGHT")]),
    (tStrings.CSC258_4, [(0, 9, "TYPE"), (10, 19, "DESCRIPTION"), (56, 66, "DATE"), (67, 70, "WEIGHT")]),
    (tStrings.CSC258_7, [(0, 10, "TYPE"), (11, 21, "DESCRIPTION"), (80, 83, "DATE"), (84, 87, "WEIGHT")]),
    (tStrings.CSC258_8, [(0, 5, "TYPE"), (9, 74, "DESCRIPTION"), (75, 85, "DATE"), (86, 88, "WEIGHT")]),
    (tStrings.CSC263_1, [(0, 10, "TYPE"), (11, 24, "DESCRIPTION"), (25, 35, "DATE"), (36, 39, "WEIGHT")]),
    (tStrings.CSC263_2, [(0, 10, "TYPE"), (11, 24, "DESCRIPTION"), (25, 35, "DATE"), (36, 39, "WEIGHT")]),
    (tStrings.CSC263_4, [(0, 10, "TYPE"), (11, 42, "DESCRIPTION"), (43, 53, "DATE"), (54, 56, "WEIGHT")]),
    (tStrings.CSC263_5, [(0, 10, "TYPE"), (11, 24, "DESCRIPTION"), (25, 35, "DATE"), (36, 39, "WEIGHT")]),
    (tStrings.CSC263_7, [(0, 4, "TYPE"), (5, 56, "DESCRIPTION"), (57, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC263_8, [(0, 4, "TYPE"), (5, 21, "DESCRIPTION"), (22, 30, "DATE"), (31, 33, "WEIGHT")]),
    (tStrings.CSC263_10, [(0, 10, "TYPE"), (11, 78, "DESCRIPTION"), (79, 82, "DATE"), (83, 86, "WEIGHT")]),
    (tStrings.CSC263_11, [(0, 5, "TYPE"), (6, 37, "DESCRIPTION"), (38, 46, "DATE"), (47, 49, "WEIGHT")]),
    (tStrings.CSC311_1, [(0, 4, "TYPE"), (5, 54, "DESCRIPTION"), (55, 63, "DATE"), (64, 66, "WEIGHT")]),
    (tStrings.CSC311_2, [(0, 3, "TYPE"), (4, 42, "DESCRIPTION"), (43, 51, "DATE"), (52, 55, "WEIGHT")]),
    (tStrings.CSC311_3, [(0, 10, "TYPE"), (11, 21, "DESCRIPTION"), (22, 32, "DATE"), (33, 36, "WEIGHT")]),
    (tStrings.CSC311_6, [(0, 9, "TYPE"), (10, 22, "DESCRIPTION"), (45, 55, "DATE"), (56, 59, "WEIGHT")]),
    (tStrings.CSC311_7, [(0, 10, "TYPE"), (11, 14, "DATE"), (15, 18, "WEIGHT")]),
    (tStrings.CSC311_8, [(0, 5, "TYPE"), (6, 56, "DESCRIPTION"), (57, 59, "WEIGHT")]),
    (tStrings.CSC324_1, [(0, 4, "TYPE"), (5, 31, "DESCRIPTION"), (32, 40, "DATE"), (41, 44, "WEIGHT")]),
    (tStrings.CSC324_3, [(0, 9, "TYPE"), (10, 17, "DESCRIPTION"), (46, 56, "DATE"), (57, 60, "WEIGHT")]),
    (tStrings.CSC324_5, [(0, 5, "TYPE"), (6, 35, "DESCRIPTION"), (36, 44, "DATE"), (45, 47, "WEIGHT")]),
    (tStrings.CSC343_1, [(0, 10, "TYPE"), (11, 23, "DESCRIPTION"), (24, 34, "DATE"), (35, 37, "WEIGHT")]),
    (tStrings.CSC343_2, [(0, 9, "TYPE"), (10, 22, "DESCRIPTION"), (23, 33, "DATE"), (34, 37, "WEIGHT")]),
    (tStrings.CSC343_3, [(0, 10, "TYPE"), (11, 23, "DESCRIPTION"), (24, 34, "DATE"), (35, 37, "WEIGHT")]),
    (tStrings.CSC343_5, [(0, 10, "TYPE"), (11, 40, "DESCRIPTION"), (75, 78, "DATE"), (79, 82, "WEIGHT")]),
    (tStrings.CSC343_6, [(0, 5, "TYPE"), (6, 17, "DESCRIPTION"), (79, 87, "DATE"), (88, 90, "WEIGHT")]),
    (tStrings.CSC373_1, [(0, 6, "DESCRIPTION"), (66, 76, "WEIGHT"), (77, 79, "DATE")]),
    (tStrings.CSC373_4, [(0, 4, "TYPE"), (5, 11, "DESCRIPTION"), (12, 22, "DATE"), (23, 25, "WEIGHT")]),
    (tStrings.CSC373_7, [(0, 4, "TYPE"), (5, 11, "DESCRIPTION"), (12, 22, "DATE"), (23, 25, "WEIGHT")]),
    (tStrings.CSC373_8, [(0, 10, "TYPE"), (11, 23, "DESCRIPTION"), (24, 34, "DATE"), (35, 38, "WEIGHT")]),
    (tStrings.CSC373_9, [(0, 10, "TYPE"), (11, 67, "DESCRIPTION"), (68, 71, "DATE"), (72, 75, "WEIGHT")]),
    (tStrings.CSC384_1, [(0, 10, "TYPE"), (11, 29, "DESCRIPTION"), (30, 40, "DATE"), (41, 44, "WEIGHT")]),
    (tStrings.CSC384_4, [(0, 10, "TYPE"), (11, 34, "DESCRIPTION"), (35, 45, "DATE"), (46, 49, "WEIGHT")]),
    (tStrings.CSC384_7, [(0, 4, "TYPE"), (5, 19, "DESCRIPTION"), (20, 30, "DATE"), (31, 35, "WEIGHT")]),
    (tStrings.CSC384_10, [(0, 4, "TYPE"), (5, 18, "DESCRIPTION"), (19, 29, "WEIGHT"), (30, 34, "DATE")]),
    (tStrings.CSC384_13, [(0, 4, "TYPE"), (5, 24, "DESCRIPTION"), (25, 35, "DATE"), (36, 40, "WEIGHT")]),
    (tStrings.CSC384_16, [(0, 9, "TYPE"), (10, 17, "DESCRIPTION"), (18, 28, "DATE"), (29, 32, "WEIGHT")]),
    (tStrings.CSC384_18, [(0, 5, "TYPE"), (6, 29, "DESCRIPTION"), (30, 33, "WEIGHT")]),
    (tStrings.CSC413_1, [(0, 3, "TYPE"), (4, 45, "DESCRIPTION"), (46, 54, "DATE"), (55, 58, "WEIGHT")]),
    (tStrings.CSC413_2, [(0, 10, "TYPE"), (11, 28, "DESCRIPTION"), (49, 59, "DATE"), (60, 63, "WEIGHT")]),
    (tStrings.CSC413_3, [(0, 10, "TYPE"), (11, 28, "DESCRIPTION"), (49, 59, "DATE"), (60, 63, "WEIGHT")]),
    (tStrings.CSC413_6, [(0, 10, "TYPE"), (11, 33, "DESCRIPTION"), (58, 68, "DATE"), (69, 72, "WEIGHT")]),
    (tStrings.CSC413_7, [(0, 10, "TYPE"), (11, 24, "DESCRIPTION"), (49, 59, "DATE"), (60, 63, "WEIGHT")]),
    (tStrings.ECO101_1, [(0, 11, "DESCRIPTION"), (12, 19, "DATE"), (44, 47, "WEIGHT")]),
    (tStrings.ECO101_2, [(0, 11, "DESCRIPTION"), (12, 19, "DATE"), (32, 35, "WEIGHT")]),
    (tStrings.ECO101_3, [(0, 11, "DESCRIPTION"), (12, 19, "DATE")]),
    (tStrings.ECO101_4, [(0, 19, "DESCRIPTION"), (20, 26, "DATE"), (38, 41, "WEIGHT")]),
    (tStrings.ECO101_5, [(0, 22, "DESCRIPTION"), (23, 29, "DATE"), (41, 44, "WEIGHT")]),
    (tStrings.ECO101_6, [(0, 17, "DESCRIPTION"), (18, 24, "DATE"), (38, 41, "WEIGHT")]),
    (tStrings.ECO200_1, [(10, 20, "DESCRIPTION"), (27, 30, "WEIGHT")]),
    (tStrings.ECO200_2, [(23, 37, "DESCRIPTION"), (44, 47, "WEIGHT")]),
    # (tStrings.ECO311_1, [(0, 5, "DESCRIPTION"), (6, 22, "DATE"), (25, 28, "WEIGHT")]),
    # (tStrings.ECO311_2, [(0, 5, "DESCRIPTION"), (6, 20, "DATE"), (23, 26, "WEIGHT")]),
    # (tStrings.ECO311_3, [(0, 5, "DESCRIPTION"), (6, 21, "DATE"), (24, 27, "WEIGHT")]),
    # (tStrings.ECO311_4, [(0, 9, "DESCRIPTION"), (10, 41, "DATE"), (44, 47, "WEIGHT")]),
    (tStrings.MAT134_1, [(0, 10, "TYPE"), (11, 39, "DESCRIPTION"), (82, 90, "DATE"), (91, 93, "WEIGHT")]),
    (tStrings.MAT134_2, [(0, 10, "TYPE"), (11, 40, "DESCRIPTION"), (60, 68, "DATE"), (69, 71, "WEIGHT")]),
    (tStrings.MAT134_3, [(0, 19, "TYPE"), (20, 43, "DESCRIPTION"), (87, 95, "DATE"), (96, 98, "WEIGHT")]),
    (tStrings.MAT134_4, [(0, 19, "TYPE"), (20, 42, "DESCRIPTION"), (79, 87, "DATE"), (88, 90, "WEIGHT")]),
    (tStrings.MAT134_7, [(0, 5, "TYPE"), (6, 18, "DESCRIPTION"), (48, 50, "WEIGHT")]),
    (tStrings.MAT134_8, [(0, 10, "DESCRIPTION"), (11, 14, "DATE"), (15, 18, "WEIGHT")]),
    (tStrings.MAT223_1, [(0, 5, "TYPE"), (6, 37, "DESCRIPTION"), (54, 62, "DATE"), (63, 65, "WEIGHT")]),
    (tStrings.MAT223_2, [(0, 5, "TYPE"), (6, 22, "DESCRIPTION"), (23, 31, "DATE"), (32, 34, "WEIGHT")]),
    (tStrings.MAT223_3, [(0, 10, "TYPE"), (11, 43, "DESCRIPTION"), (44, 52, "DATE"), (53, 56, "WEIGHT")]),
    (tStrings.MAT223_4, [(0, 9, "TYPE"), (10, 17, "DESCRIPTION"), (18, 28, "DATE"), (29, 32, "WEIGHT")]),
    (tStrings.MAT223_6, [(0, 10, "DESCRIPTION"), (11, 14, "DATE"), (15, 18, "WEIGHT")]),
    (tStrings.MAT301_1, [(0, 9, "TYPE"), (10, 21, "DESCRIPTION"), (22, 32, "DATE"), (33, 38, "WEIGHT")]),
    (tStrings.MAT301_3, [(0, 10, "TYPE"), (11, 41, "DESCRIPTION"), (42, 50, "DATE"), (51, 54, "WEIGHT")]),
    (tStrings.MAT301_4, [(0, 10, "TYPE"), (11, 19, "DATE"), (20, 23, "WEIGHT")]),
    (tStrings.MAT334_1, [(0, 10, "TYPE"), (11, 33, "DESCRIPTION"), (71, 79, "DATE"), (80, 83, "WEIGHT")]),
    (tStrings.MAT334_2, [(0, 9, "DESCRIPTION"), (35, 45, "DATE"), (46, 49, "WEIGHT")]),
    (tStrings.MAT334_3, [(0, 11, "DESCRIPTION"), (36, 46, "DATE"), (47, 50, "WEIGHT")]),
    (tStrings.MAT334_4, [(0, 10, "DESCRIPTION"), (19, 22, "DATE"), (23, 26, "WEIGHT")]),
    (tStrings.MAT344_1, [(0, 5, "TYPE"), (6, 32, "DESCRIPTION"), (33, 43, "DATE"), (44, 47, "WEIGHT")]),
    (tStrings.MAT344_2, [(0, 5, "TYPE"), (6, 33, "DESCRIPTION"), (46, 56, "DATE"), (57, 60, "WEIGHT")]),
    (tStrings.MAT344_3, [(0, 5, "TYPE"), (6, 31, "DESCRIPTION"), (44, 54, "DATE"), (55, 58, "WEIGHT")]),
    (tStrings.PHY100_1, [(0, 10, "TYPE"), (11, 55, "DESCRIPTION"), (76, 84, "DATE"), (85, 88, "WEIGHT")]),
    (tStrings.PHY100_2, [(0, 10, "TYPE"), (11, 32, "DESCRIPTION"), (78, 88, "DATE"), (89, 92, "WEIGHT")]),
    (tStrings.PHY100_3, [(0, 9, "TYPE"), (10, 23, "DESCRIPTION"), (78, 88, "DATE"), (89, 92, "WEIGHT")]),
    (tStrings.PHY100_4, [(0, 10, "TYPE"), (11, 33, "DESCRIPTION"), (80, 83, "DATE"), (84, 87, "WEIGHT")]),
    (tStrings.STA258_1, [(0, 6, "DESCRIPTION"), (46, 63, "DATE"), (64, 67, "WEIGHT")]),
    (tStrings.STA258_2, [(0, 6, "DESCRIPTION"), (46, 61, "DATE"), (62, 65, "WEIGHT")]),
    (tStrings.STA258_3, [(0, 14, "DESCRIPTION"), (35, 38, "WEIGHT")]),
    (tStrings.STA258_4, [(0, 19, "DESCRIPTION"), (20, 22, "WEIGHT")]),
    (tStrings.STA258_5, [(0, 10, "TYPE"), (11, 32, "DESCRIPTION"), (33, 36, "DATE"), (37, 40, "WEIGHT")]),
    (tStrings.STA260_1, [(0, 9, "TYPE"), (10, 16, "DESCRIPTION"), (17, 27, "DATE"), (28, 31, "WEIGHT")]),
    (tStrings.STA260_2, [(0, 9, "TYPE"), (10, 16, "DESCRIPTION"), (17, 27, "DATE"), (28, 31, "WEIGHT")]),
    (tStrings.STA260_3, [(0, 10, "TYPE"), (11, 16, "DESCRIPTION"), (17, 20, "DATE"), (21, 24, "WEIGHT")]),
]   

validation_data = [
    (vStrings.STA302_1, [(0, 33, "DESCRIPTION"), (48, 55, "DATE"), (66, 68, "WEIGHT")]),
    (vStrings.STA302_2, [(0, 9, "DESCRIPTION"), (25, 32, "DATE"), (49, 52, "WEIGHT")]),
    (vStrings.STA302_4, [(0, 26, "DESCRIPTION"), (27, 37, "DATE"), (48, 50, "WEIGHT")]),
    (vStrings.STA302_5, [(0, 28, "DESCRIPTION"), (29, 40, "DATE"), (51, 54, "WEIGHT")]),
    (vStrings.STA302_6, [(0, 10, "DESCRIPTION"), (55, 58, "WEIGHT")]),
    (vStrings.STA304_2, [(0, 6, "DESCRIPTION"), (7, 14, "DATE"), (15, 18, "WEIGHT")]),
    (vStrings.STA304_3, [(0, 6, "DESCRIPTION"), (7, 14, "DATE"), (15, 18, "WEIGHT")]),
    (vStrings.STA304_4, [(0, 5, "DESCRIPTION"), (6, 9, "DATE"), (10, 13, "WEIGHT")]),
    (vStrings.STA304_6, [(0, 26, "DESCRIPTION"), (31, 38, "DATE"), (39, 41, "WEIGHT")]),
    (vStrings.STA304_7, [(0, 15, "DESCRIPTION"), (16, 23, "DATE"), (24, 26, "WEIGHT")]),
]

# Test if label spans are valid
for i, (text, entities) in enumerate(training_data):
    doc = nlp.make_doc(text)
    for start, end, label in entities:
        span = doc.char_span(start, end, label=label)
        if span is None:
            print(text)
            print(f"Invalid span in item {i}: ({start}, {end}, {label})")
            print(f"Text segment: {repr(text[start:end])}")
        else:
            print(f"Span OK in item {i}: {label} - {repr(span.text)}")

print("TESTING VALIDATION ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n\n\n\n")
for i, (text, entities) in enumerate(validation_data):
    doc = nlp.make_doc(text)
    for start, end, label in entities:
        span = doc.char_span(start, end, label=label)
        if span is None:
            print(text)
            print(f"Invalid span in item {i}: ({start}, {end}, {label})")
            print(f"Text segment: {repr(text[start:end])}")
        else:
            print(f"Span OK in item {i}: {label} - {repr(span.text)}")

# Prepare training data for spacy
db = DocBin()
for text, annotations in training_data:
    doc = nlp(text)
    ents = []
    for start, end, label in annotations:
        span = doc.char_span(start, end, label=label)
        ents.append(span)
    doc.ents = ents
    db.add(doc)
db.to_disk(train_save_path)

# Prepare validation data for spacy
db2 = DocBin()
for text, annotations in validation_data:
    doc = nlp(text)
    ents = []
    for start, end, label in annotations:
        span = doc.char_span(start, end, label=label)
        ents.append(span)
    doc.ents = ents
    db2.add(doc)
db2.to_disk(validation_save_path)