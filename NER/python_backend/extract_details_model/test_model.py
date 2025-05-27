import spacy
import os

script_dir = os.path.dirname(os.path.abspath(__file__))
model_dir = os.path.abspath(os.path.join(script_dir, "model", "model-best"))
testing_txt_dir = os.path.abspath(os.path.join(script_dir, "data", "testing_txts"))

nlp = spacy.load(model_dir)

# Loop over all test txts
for filename in os.listdir(testing_txt_dir):
    print(f"Testing: {filename}")
    file = os.path.join(testing_txt_dir, filename)
    with open(file, "r", encoding="utf-8") as f:
        test_item = f.read()
        
        # Create doc object
        doc = nlp(test_item)

        for ent in doc.ents:
            # Print the entity text and its label
            print(ent.label_, ent.text)

    print("\n")