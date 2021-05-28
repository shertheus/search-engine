import re


class Concept:
    def __init__(self, _id, name):
        self.id = _id
        self.name = name
        self.related = []
        self.same = []
        self.url = ""
        self.sub = []  # 该类的子类
        self.instance = []

    def add_related(self, related):
        self.related.append(related)

    def set_url(self, url):
        self.url = url

    def add_sub(self, sub):
        self.sub.append(sub)

    def add_same(self, sub):
        self.same.append(sub)

    def add_instance(self, instance):
        self.instance.append(instance)


class Instance:
    def __init__(self, _id, name):
        self.id = _id
        self.name = name
        self.supplement = []
        self.alias = []
        self.related = []
        self.same = []
        self.url = ""
        self.comment = ""
        self.comment_ins = []
        self.pro = []

    def add_pro(self, pro):
        self.pro.append(pro)

    # 新加的设置supplement alias
    def set_supplement_alias(self, str, f):
        if f == 1:
            self.supplement.append(str)
        else:
            self.alias.append(str)

    def add_related(self, related):
        self.related.append(related)

    def set_url(self, url):
        self.url = url

    def add_same(self, sub):
        self.same.append(sub)

    def set_comment(self, comment):
        # TODO parse comment to get comment_ins
        # self.comment_ins
        words = re.findall('\[\[(.*?)\]\]', comment)
        for w in words:
            nid = re.findall('[a-z]+[\d]+', w)
            nw = re.findall('\|.+', w)
            nnw = nid
            if len(nw) != 0:
                nnw[0] = nw[0][1:]
            comment = comment.replace('[[' + w + ']]', nnw[0])
            if nid[0] not in self.comment_ins:
                self.comment_ins.append(nid[0])
        self.comment = comment


class Property:
    def __init__(self, _id, label, fullname=None):
        self.id = _id
        self.label = label
        self.fullname = fullname

    # 新加的设置supplement
    def set_fullname(self, fullname):
        self.fullname = fullname
# class Noun:
#     def __init__(self, _id, name):
#         self.id = _id
#         self.name = name
