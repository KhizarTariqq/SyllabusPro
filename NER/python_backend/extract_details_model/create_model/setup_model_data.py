import training_data_strings as tStrings
import validation_data_strings as vStrings
import spacy
from spacy.tokens import DocBin

nlp = spacy.blank("en")

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
    (tStrings.CSC263_1, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC263_2, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC263_4, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC263_5, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC263_7, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC263_8, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC263_10, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC263_11, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC311_1, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC311_2, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC311_3, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC311_6, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC311_7, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC311_8, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC324_1, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC324_3, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC324_5, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC343_1, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC343_2, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC343_3, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC343_5, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC343_6, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC373_1, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC373_4, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC373_7, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC373_8, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC373_9, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC384_1, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC384_4, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC384_7, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC384_10, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC384_13, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC384_16, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC384_18, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC413_1, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC413_2, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC413_3, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC413_6, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.CSC413_7, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.ECO101_1, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.ECO101_2, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.ECO101_3, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.ECO101_4, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.ECO101_5, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.ECO101_6, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.ECO200_1, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.ECO200_2, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.ECO311_1, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.ECO311_2, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.ECO311_3, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.ECO311_4, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.MAT134_1, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.MAT134_2, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.MAT134_3, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.MAT134_4, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.MAT134_7, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.MAT134_8, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.MAT223_1, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.MAT223_2, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.MAT223_3, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.MAT223_4, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.MAT223_6, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.MAT301_1, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.MAT301_3, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.MAT301_4, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.MAT334_1, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.MAT334_2, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.MAT334_3, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.MAT334_4, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.MAT344_1, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.MAT334_2, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.MAT334_3, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.PHY100_1, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.PHY100_2, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.PHY100_3, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.PHY100_4, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.STA258_1, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.STA258_2, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.STA258_3, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.STA258_4, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.STA258_5, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.STA260_1, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.STA260_2, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
    (tStrings.STA260_3, [(0, 10, "TYPE"), (11, 54, "DESCRIPTION"), (55, 65, "DATE"), (66, 68, "WEIGHT")]),
]   

validation_data = [

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
            print(f"Span OK in item {i}: {repr(span.text)}")

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
db.to_disk("./train.spacy")

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
db2.to_disk("./dev.spacy")