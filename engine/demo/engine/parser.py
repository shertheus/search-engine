from xml.dom.minidom import parse
import xml.dom.minidom
import os

# tree = xml.dom.minidom.parse("./test.xml")
# root = tree.childNodes[0]
# test1 = root.childNodes[0].getAttribute("value")
root_path = "C:\\Users\\shertheus\\Downloads\\data"


class Parser:
    def __init__(self, name, path):
        self.name = name
        self.path = path


if __name__ == "__main__":
    xml_paths = os.listdir(root_path)
    for xml_path in xml_paths:
        tmp_dir_path = os.path.join(root_path, xml_path)
        dir_paths = os.listdir(tmp_dir_path)
        for dir_path in dir_paths:
            tmp_file_path = os.path.join(tmp_dir_path, dir_path)
            files = os.listdir(tmp_file_path)
            if len(files) > 1 and len(files) % 2 != 0:
                print(tmp_file_path)
                break
            # print(tmp_file_path)
            # for file in files:
            #     print(file)
            #     # if file.endswith("txt"):
            #     #     print(file)
            #     # break