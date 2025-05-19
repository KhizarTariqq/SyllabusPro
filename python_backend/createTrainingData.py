import trainingDataStrings as strings
import spacy
from spacy.tokens import DocBin

nlp = spacy.blank("en")

training_data = [
  (strings.CSC413, [(3905, 3963, "SYLLABUSITEM"), (3964, 4027, "SYLLABUSITEM"), (4028, 4091, "SYLLABUSITEM"), (4092, 4150, "SYLLABUSITEM"), (4151, 4209, "SYLLABUSITEM"), (4210, 4282, "SYLLABUSITEM"), (4283, 4346, "SYLLABUSITEM")]),
  (strings.STA260, [(4469, 4500, "SYLLABUSITEM"), (4501, 4532, "SYLLABUSITEM"), (4533, 4557,"SYLLABUSITEM")]),
  (strings.STA302, [(8707, 8774, "SYLLABUSITEM"), (8775, 8843, "SYLLABUSITEM"), (8844, 8901, "SYLLABUSITEM"), (8902, 8946, "SYLLABUSITEM"), (8947, 8999, "SYLLABUSITEM"), (9024, 9081, "SYLLABUSITEM"), (9082, 9132, "SYLLABUSITEM"), (9133, 9187, "SYLLABUSITEM"), (9188, 9246, "SYLLABUSITEM")]),
  (strings.PHY100, [(8707, 8774, "SYLLABUSITEM"), (8775, 8843, "SYLLABUSITEM"), (8844, 8901, "SYLLABUSITEM"), (8902, 8946, "SYLLABUSITEM"), (8947, 8999, "SYLLABUSITEM"), (9024, 9081, "SYLLABUSITEM"), (9082, 9132, "SYLLABUSITEM"), (9133, 9187, "SYLLABUSITEM"), (9188, 9246, "SYLLABUSITEM")]),

]
# the DocBin will store the example documents
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