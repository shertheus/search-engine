# -*- coding: utf-8 -*-
import io
import sys

sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

from Element import Concept, Instance, Property
import os
import re

concept_list = {}  # 格式concept["concept_id"] = Concept
instance_list = {}
property_list = {}

path = os.path.dirname(os.path.abspath(__file__)) + '\\ttl'


def set_concept():
    with open(path + "\\xlore.concept.list.ttl", encoding='utf-8') as f:
        for line in f:
            if line[0] != '<':
                continue
            id = re.findall(r'[<](.*?)[>]', line)
            name = re.findall(r'["](.*?)["]', line)
            if len(name) == 0:
                continue

            # print (id[0] + ' ' + name[0])
            new_concept = Concept(id[0], name[0])
            concept_list[id[0]] = new_concept
    f.close()
    print(1)
    with open(path + "\\xlore.concept.related.ttl", encoding='utf-8') as f:
        for line in f:
            if line[0] != '<':
                continue
            id = re.findall(r'[a-z]+[\d]+', line)
            concept_list[id[0]].add_related(id[1])
    f.close()
    print(2)
    with open(path + "\\xlore.concept.sameAs.ttl", encoding='utf-8') as f:
        for line in f:
            if line[0] != '<':
                continue
            id = re.findall(r'[a-z]+[\d]+', line)
            concept_list[id[0]].add_same(id[1])
    f.close()
    print(3)
    with open(path + "\\xlore.concept.url.ttl", encoding='utf-8') as f:
        for line in f:
            if line[0] != '<':
                continue
            id = re.findall(r'[<](.*?)[>]', line)
            url = re.findall(r'["](.*?)["]', line)

            # print (id[0] + ' ' + url[0])
            concept_list[id[0]].set_url(url[0])
    f.close()
    print(4)


def set_instance():
    print(0)
    with open(path + "\\xlore.instance.list.ttl", encoding='utf-8') as f:
        for line in f:
            if line[0] != '<':
                continue
            id = re.findall(r'[<](.*?)[>]', line)
            name = re.findall(r'["](.*?)["]', line)
            if len(name) == 0:
                continue
            if id[0] not in instance_list:
                new_instance = Instance(id[0], name[0])
                instance_list[id[0]] = new_instance
            else:
                if line.find('alias') != -1:
                    instance_list[id[0]].set_supplement_alias(name[0], 0)
                if line.find('supplement') != -1:
                    instance_list[id[0]].set_supplement_alias(name[0], 1)

    f.close()
    print(1)
    with open(path + "\\xlore.instance.related.ttl", encoding='utf-8') as f:
        for line in f:
            if line[0] != '<':
                continue
            id = re.findall(r'[a-z]+[\d]+', line)
            instance_list[id[0]].add_related(id[1])
    f.close()
    print(2)
    with open(path + "\\xlore.instance.sameAs.ttl", encoding='utf-8') as f:
        for line in f:
            if line[0] != '<':
                continue
            id = re.findall(r'[a-z]+[\d]+', line)
            instance_list[id[0]].add_same(id[1])
    f.close()
    print(3)
    with open(path + "\\xlore.instance.text.ttl", encoding='utf-8') as f:
        for line in f:
            if line[0] != '<':
                continue
            id = re.findall(r'[<](.*?)[>]', line)
            comment = re.findall(r'["](.*?)["]', line)
            instance_list[id[0]].set_comment(comment[0])
    f.close()
    print(4)
    with open(path + "\\xlore.instance.url.ttl", encoding='utf-8') as f:
        for line in f:
            if line[0] != '<':
                continue
            id = re.findall(r'[<](.*?)[>]', line)
            url = re.findall(r'["](.*?)["]', line)
            instance_list[id[0]].set_url(url[0])
    f.close()
    print(5)


def set_property():
    print(0)
    with open(path + "\\xlore.property.list.ttl", encoding='utf-8') as f:
        for line in f:
            if line[0] != '<':
                continue
            id = re.findall(r'[<](.*?)[>]', line)
            name = re.findall(r'["](.*?)["]', line)
            if len(name) == 0:
                continue

            # print (id[0] + ' ' + name[0])
            if id[0] not in property_list:
                new_property = Property(id[0], name[0])
                property_list[id[0]] = new_property
            else:
                property_list[id[0]].set_fullname(name[0])
    f.close()
    print(1)


def set_of():
    with open(path + "\\xlore.infobox.ttl", encoding='utf-8') as f:
        for line in f:
            if line[0] != '<':
                continue
            id = re.findall(r'[a-z]+[\d]+', line)
            name = re.findall(r'["](.*?)["]', line)
            instance_list[id[0]].add_pro(id[1] + '-' + name[0])
    f.close()

    with open(path + "\\xlore.subclassOf.ttl", encoding='utf-8') as f:
        for line in f:
            if line[0] != '<':
                continue
            id = re.findall(r'[a-z]+[\d]+', line)
            concept_list[id[1]].add_sub(id[0])
    f.close()
    with open(path + "\\xlore.instanceOf.ttl", encoding='utf-8') as f:
        for line in f:
            if line[0] != '<':
                continue
            id = re.findall(r'[a-z]+[\d]+', line)
            concept_list[id[1]].add_instance(id[0])
    f.close()


def main():
    set_concept()
    set_instance()
    set_property()
    set_of()


def output():
    count = 0
    with open(path + "\\concept_list.txt", 'w') as f:
        for w in concept_list:
            if count >= 5:
                break
            count = count + 1
            w = concept_list[w]
            f.write('id:' + w.id + '|name:' + w.name + '|url:' + w.url + '\n|related:')
            f.writelines(w.related)
            f.write('\n|same:')
            f.writelines(w.same)
            f.write('\n|sub:')
            f.writelines(w.sub)
            f.write('\n|instance:')
            f.writelines(w.instance)
            f.write('\n##################')
    f.close()
    count = 0
    with open(path + "\\instance_list.txt", 'w') as f:
        for w in instance_list:
            if count >= 5:
                break
            count = count + 1
            w = instance_list[w]
            f.write('id:' + w.id + '|name:' + w.name
                    + '|url:' + w.url + '\n|related:')
            f.writelines(w.related)
            f.write('\n|same:')
            f.writelines(w.same)
            f.write('\n|comment:')
            f.writelines(w.comment)
            f.write('\n|comment_ins:')
            f.writelines(w.comment_ins)
            f.write('\n|pro:')
            f.writelines(w.pro)
            f.write('\n##################')
    f.close()
    count = 0
    with open(path + "\\property_list.txt", 'w') as f:
        for w in property_list:
            if count >= 5:
                break
            count = count + 1
            w = property_list[w]
            f.write('id:' + w.id + '\n##################')
    f.close()


if __name__ == "__main__":
    main()
    # output()
