from django.contrib.auth.models import User
from rest_framework import status, generics
from rest_framework.decorators import api_view
from rest_framework.parsers import JSONParser
from rest_framework.response import Response
from rest_framework.views import APIView
from .serializers import UserSerializer
from snippets.models import Snippet
from snippets.serializers import SnippetSerializer


# Create your views here.

class SnippetList(APIView):
    def get(self, request, format=None):
        snippet = Snippet.objects.all()
        serializer = SnippetSerializer(snippet, many=True)
        return Response(serializer.data)

    def post(self, request, format=None):
        serializer = SnippetSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_202_ACCEPTED)
        else:
            return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def perform_create(self, serializer):
        serializer.save(owner=self.request.user)


class SnippetDetail(APIView):
    def get_object(self, pk):
        try:
            return Snippet.objects.get(pk=pk)
        except Snippet.DoesNotExist:
            return Response(status=status.HTTP_404_NOT_FOUND)

    def get(self, request, pk, format=None):
        snippet = self.get_object(pk)
        serializer = SnippetSerializer(snippet)
        return Response(serializer.data)

    def post(self, request, pk, format: None):
        snippet = self.get_object(pk)
        data = JSONParser().parse(request)
        serializer = SnippetSerializer(snippet, data=data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, pk, format=None):
        snippet = self.get_object(pk)
        snippet.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)


class UserList(APIView):
    def get(self, request):
        queryset = User.objects.all()
        serialize = UserSerializer(queryset, many=True)
        return Response(serialize.data)


class UserDetail(APIView):
    def get(self, request, pk):
        user = User.objects.get(pk=pk)
        serialize = UserSerializer(user)
        return Response(serialize.data)
