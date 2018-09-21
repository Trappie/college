(define (cddr s)
 (cdr (cdr s)))

    (define (cadr s)
     (car (cdr s))
    )

    (define (caddr s)
     (car (cddr s))
    )

(define (sign x)
 (cond
  ((< x 0) -1)
  ((= x 0) 0)
  (else 1)
 )
 )

(define (square x) (* x x))

(define (pow b n)
 (cond
  ((= n 0) 1)
  ((odd? n) (* b (square (pow b (/ (- n 1) 2))))); n is odd
  ((even? n) (square (pow b (/ n 2)))); n is even
 )
 )

    (define (ordered? s)
     (if (> (length s) 1)
      (if (> (car s) (cadr s))
#f; if the first is bigger than the second
       (ordered? (cdr s))
      )
#t
     )
    )

(define (nodots s)
 (cond
  ((null? s) ())
  ((not (pair? s)) (cons s nil))
  ((pair? (car s)) (cons (nodots (car s)) (nodots (cdr s)))); if the first element of s is pair
  (else (cons (car s) (nodots (cdr s))))
 )
 )

    ; Sets as sorted lists

(define (empty? s) (null? s))

(define (contains? s v)
 (cond 
  ((empty? s) #f)
  ((> (car s) v) #f)
  ((= (car s) v) #t)
  (else (contains? (cdr s) v)) ; replace this line
 )
)

; Equivalent Python code, for your reference:
;
; def empty(s):
    ;     return s is Link.empty
    ;
    ; def contains(s, v):
        ;     if empty(s):
            ;         return False
            ;     elif s.first > v:
            ;         return False
            ;     elif s.first == v:
            ;         return True
            ;     else:
            ;         return contains(s.rest, v)

(define (add s v)
 (cond 
  ((empty? s) (list v))
  ((< v (car s)) (cons v s))
  ((= v (car s)) s)
  (else (cons (car s) (add (cdr s) v))) ; replace this line
 )
)

(define (intersect s t)
 (cond 
  ((or (empty? s) (empty? t)) nil)
  ((= (car s) (car t)) (cons (car s) (intersect (cdr s) (cdr t))))
  ((> (car s) (car t)) (intersect s (cdr t)))
  (else (intersect (cdr s) t)) ; replace this line
 ))

; Equivalent Python code, for your reference:
;
; def intersect(set1, set2):
    ;     if empty(set1) or empty(set2):
        ;         return Link.empty
        ;     else:
        ;         e1, e2 = set1.first, set2.first
        ;         if e1 == e2:
        ;             return Link(e1, intersect(set1.rest, set2.rest))
        ;         elif e1 < e2:
        ;             return intersect(set1.rest, set2)
        ;         elif e2 < e1:
        ;             return intersect(set1, set2.rest)

(define (union s t)
 (cond 
  ((empty? s) t)
  ((empty? t) s)
  ((= (car s) (car t)) (cons (car s) (union (cdr s) (cdr t))))
  ((< (car s) (car t)) (cons (car s) (union (cdr s) t)))
  (else (cons (car t) (union s (cdr t)))) ; replace this line
 ))
