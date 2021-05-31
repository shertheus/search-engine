from django.shortcuts import render

# Create your views here.
from django.shortcuts import render
from django.http import JsonResponse, HttpResponse
from .models import Concept
import json
from django.core.exceptions import ValidationError
from py4j.java_gateway import JavaGateway

gateway = JavaGateway()


def res(request):
    def gen_response(code: int, data: str):
        return JsonResponse({
            'code': code,
            'data': data
        }, status=code)

    keyword = request.GET.get('keyword')
    res = gateway.entry_point.searchByWord(keyword, "")
    print(res)
    # try:
    #     res = json.dumps(res)
    #     print(res)
    # except ValidationError as e:
    #     print("OOOOOOO")

    return gen_response(200, res)
