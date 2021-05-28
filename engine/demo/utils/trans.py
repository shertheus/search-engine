from py4j.java_gateway import JavaGateway
import json
from Parser import parser




gateway = JavaGateway()

def main():
    # 调用Java-EntryPoint中的randInt函数
    p = parser()
    p.set_concept()
    for c in p.concept_list:
        concept = p.concept_list[c]
        related = json.dumps(concept.related, ensure_ascii=False)
        same = json.dumps(concept.same, ensure_ascii=False)
        sub = json.dumps(concept.sub, ensure_ascii=False)
        instance = json.dumps(concept.instance, ensure_ascii=False)
        gateway.entry_point.getString(c, concept.name, concept.url, related, same, sub, instance)
    # gateway.entry_point.getString("c", "concept.name", "concept.url", "related", "same", "sub", "instance")
    a = gateway.entry_point.randInt("c", "concept.name", "concept.url", "related", "same", "sub", "instance")
    print(a)

if __name__ == '__main__':
    main()
