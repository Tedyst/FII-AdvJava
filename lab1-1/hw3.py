import urllib
import urllib.request

def get_webpage(url):
    return urllib.request.urlopen(url).read()

d = get_webpage('http://localhost:8080/lab1_1_war_exploded/hw-servlet-1?numEdges=5&numVertices=5')
print(d)