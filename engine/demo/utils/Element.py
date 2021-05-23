class Concept:
    def __init__(self, _id, name):
        self.id = _id
        self.name = name
        self.related = []
        self.same = []
        self.url = ""
        self.sub = []
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
    def __init__(self, _id, name, supplement=None, alias=None):
        self.id = _id
        self.name = name
        self.supplement = supplement
        self.alias = alias
        self.related = []
        self.same = []
        self.url = ""
        self.comment = ""
        self.comment_ins = []

    def add_related(self, related):
        self.related.append(related)

    def set_url(self, url):
        self.url = url

    def add_same(self, sub):
        self.same.append(sub)

    def set_comment(self, comment):
        self.comment = comment
        # TODO parse comment to get comment_ins
        # self.comment_ins


class Property:
    def __init__(self, _id, label, fullname=None):
        self.id = _id
        self.label = label
        self.fullname = fullname

# class Noun:
#     def __init__(self, _id, name):
#         self.id = _id
#         self.name = name
