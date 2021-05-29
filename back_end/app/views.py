from django.shortcuts import render

# Create your views here.
from django.shortcuts import render
from django.http import JsonResponse, HttpResponse
from .models import Concept
import json
from django.core.exceptions import ValidationError


def res(request):
    def gen_response(code: int, data: str):
        return JsonResponse({
            'code': code,
            'data': data
        }, status=code)
    print(request.GET.get('keyword'))
    # try:
    #     data = json.loads(request.body)
    # except ValidationError as e:
    #     return gen_response(400, "Validation Error of user: {}".format(e))
    # print(data['title'])

    l = []
    for i in range(0, 10):
        l.append(str(i))
    response = {
        'id': "id",
        'data': [{
            'title': li,
        } for li in l],
    }
    return JsonResponse(response, status=200)
