def read_char_range(file_path, start, end):
    """
    Reads characters from `start` to `end` (exclusive) in the file at `file_path`.
    """
    with open(file_path, 'r', encoding='utf-8') as f:
        text = f.read()
    return text[start:end]

#[2537,2576,"SYLLABUSITEM"],[2577,2616,"SYLLABUSITEM"],[2675,2713,"SYLLABUSITEM"],[2714,2770,"SYLLABUSITEM"],[2771,2810,"SYLLABUSITEM"],[2811,2876,"SYLLABUSITEM"],[2877,2945,"SYLLABUSITEM"],[2946,2979,"SYLLABUSITEM"],[2980,3046,"SYLLABUSITEM"],[3047,3133,"SYLLABUSITEM"],[3134,3183,"SYLLABUSITEM"]
file_path = 'txts/20231_MAT301H5S_LEC0101.txt'
start = 1772
end = 1810

chunk = read_char_range(file_path, start, end)
print(f"Characters {start}-{end}:\n{chunk}")
