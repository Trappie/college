

def outter(obj, duration):
    count = 0
    def inner(para):
        global count
        global duration
        print(count)
        print(duration) 
        print(para) 
   obj.action = inner 

some_obj = 6
outter(some_obj, 3)
obj.action('wobushi')
