from py4j.java_gateway import JavaGateway
import json
import time
from Parser import parser




gateway = JavaGateway()

def main():
    # 调用Java-EntryPoint中的randInt函数
    p = parser()
    p.set_concept()
    p.set_instance()
    p.set_property()
    p.set_of()
    for c in p.concept_list:
        concept = p.concept_list[c]
        related = json.dumps(concept.related, ensure_ascii=False)
        same = json.dumps(concept.same, ensure_ascii=False)
        sub = json.dumps(concept.sub, ensure_ascii=False)
        instance = json.dumps(concept.instance, ensure_ascii=False)
        gateway.entry_point.getConceptString(c, concept.name, concept.url, related, same, sub, instance)
    print("concept done")
    time.sleep(1)
    gateway.entry_point.set_commit()
    print("commiting")
    time.sleep(1)
    for i in p.instance_list:
        ins = p.instance_list[i]
        supplement = json.dumps(ins.supplement, ensure_ascii=False)
        alias = json.dumps(ins.alias, ensure_ascii=False)
        related = json.dumps(ins.related, ensure_ascii=False)
        same = json.dumps(ins.same, ensure_ascii=False)
        comment_ins = json.dumps(ins.comment_ins, ensure_ascii=False)
        pro = json.dumps(ins.pro, ensure_ascii=False)
        gateway.entry_point.getInstanceString(i, ins.name, ins.url, ins.comment, supplement, alias,
             related, same, comment_ins, pro)

    print("instance done")
    time.sleep(1)
    gateway.entry_point.set_commit()
    print("commiting")
    for pr in p.property_list:
        property = p.property_list[pr]
        gateway.entry_point.getPropertyString(pr, property.label, property.fullname)

    print("property done")
    time.sleep(1)
    gateway.entry_point.set_commit()
    print("commiting")
    time.sleep(1)
    print("closing")
    gateway.shutdown()

if __name__ == '__main__':
    main()
