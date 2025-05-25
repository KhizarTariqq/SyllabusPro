import re

def read_char_range(file_path, start, end):
    """
    Reads characters from `start` to `end` (exclusive) in the file at `file_path`.
    """
    with open(file_path, 'r', encoding='utf-8') as f:
        text = f.read()
    return text[start:end]

def read_all_ranges_from_string(file_path, range_string):
    """
    Parses a string with character ranges and reads the corresponding text from the file.
    Example input: '(2904, 2933, "SYLLABUSITEM"), (3001, 3040, "SYLLABUSITEM")'
    """
    # Find all tuples of the form (start, end, "LABEL")
    pattern = r'\(\s*(\d+)\s*,\s*(\d+)\s*,\s*".*?"\s*\)'
    matches = re.findall(pattern, range_string)

    # Read and print each range
    for start_str, end_str in matches:
        start = int(start_str)
        end = int(end_str)
        chunk = read_char_range(file_path, start, end)
        print(f"Characters {start}-{end}:\n{chunk}\n{'-'*40}")

range_string = '(2904, 2933, "SYLLABUSITEM"), (3001, 3040, "SYLLABUSITEM"), (3041, 3101, "SYLLABUSITEM"), (3102, 3141, "SYLLABUSITEM"), (3142, 3196, "SYLLABUSITEM")'
file_path = '/home/whatsanoutside/syllabusprogit/SyllabusPro/NER/python_backend/extractItemsModel/data/training_txts/20249_CHM211H5F_LEC0101.txt'

read_all_ranges_from_string(file_path, range_string)
