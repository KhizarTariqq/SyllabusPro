def read_char_range(file_path, start, end):
    """
    Reads characters from `start` to `end` (exclusive) in the file at `file_path`.
    """
    with open(file_path, 'r', encoding='utf-8') as f:
        text = f.read()
    return text[start:end]

file_path = 'txts/1 Syllabus 20241_PHY100H5S_LEC0101.txt'
start = 4512
end = 4599

chunk = read_char_range(file_path, start, end)
print(f"Characters {start}-{end}:\n{chunk}")
