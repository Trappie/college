
from scheme import *
#expr = read_line('(define (factorial n k) (if (= n 0) k (factorial (- n 1) (* k n))))')
#frame = create_global_frame()
#scheme_eval(expr, frame)
line = '(+ 1 2)'
line = '1'
#line = 'a'
expr = read_line(line)
frame = create_global_frame()
print(self_evaluating(expr))
print(scheme_symbolp(expr))
print(scheme_eval(expr, frame))


