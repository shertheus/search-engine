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
    print(request.GET.keys())
    print(type(request.GET.get('ftype')))
    ftype = request.GET.get('ftype')
    if ftype == 'word':
        keyword = request.GET.get('keyword')
        ttype = request.GET.get('type')
        print(ttype)
        res = gateway.entry_point.searchByWord(keyword, ttype)
    else:
        id = request.GET.get('id')
        print(id)
        res = gateway.entry_point.searchById(id)
        print(res)


    return gen_response(200, res)
