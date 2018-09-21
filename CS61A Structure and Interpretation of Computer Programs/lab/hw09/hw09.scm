(define (how-many-dots s)
    (cond
        ((number? s) 1)
        ((null? s) 0)
        ((pair? s) 
            (if (pair? (car s))
                (+ (how-many-dots (car s)) (how-many-dots (cdr s)))
                (how-many-dots (cdr s))
            )
        )
    )
)

(define (cadr s) (car (cdr s)))
(define (caddr s) (cadr (cdr s)))


; derive returns the derivative of EXPR with respect to VAR
(define (derive expr var)
  (cond ((number? expr) 0) ; if the E is number, then dE is 0
        ((variable? expr) (if (same-variable? expr var) 1 0)) ; 
        ((sum? expr) (derive-sum expr var))
        ((product? expr) (derive-product expr var))
        ((exp? expr) (derive-exp expr var))
        (else 'Error)))

; Variables are represented as symbols
(define (variable? x) (symbol? x)) ; if x is a symbol, then it's a variable
(define (same-variable? v1 v2)
  (and (variable? v1) (variable? v2) (eq? v1 v2))) ; when v1 and v2 are both variable and they are equal, they are considered the same variable


; Numbers are compared with =
(define (=number? expr num)
  (and (number? expr) (= expr num)))

; Sums are represented as lists that start with +.
(define (make-sum a1 a2)
  (cond ((=number? a1 0) a2) ; if a1 is 0, return a2
        ((=number? a2 0) a1) ; if a1 is not 0 but a2 is 0, return a1
        ((and (number? a1) (number? a2)) (+ a1 a2)) ; if a1 a2 != 0, and they are both number, return their sum
        (else (list '+ a1 a2)))) ;else return the sum of a1 and a2 inwhich at least one of the two is not number

(define (sum? x)
  (and (list? x) (eq? (car x) '+))) ;if the first element of the list is +, then this list is considered a sum
(define (addend s) (cadr s))
(define (augend s) (caddr s))

; Products are represented as lists that start with *.
; similar as make-sum
(define (make-product m1 m2)
  (cond ((or (=number? m1 0) (=number? m2 0)) 0)
        ((=number? m1 1) m2)
        ((=number? m2 1) m1)
        ((and (number? m1) (number? m2)) (* m1 m2))
        (else (list '* m1 m2))))
(define (product? x)
  (and (list? x) (eq? (car x) '*)))
(define (multiplier p) (cadr p))
(define (multiplicand p) (caddr p))

(define (derive-sum expr var)
    ; the expression should be a sum
    (make-sum (derive (addend expr) var) (derive (augend expr) var))
    ;(derive expr var)
)

(define (derive-product expr var)
    (define m1 (multiplier expr))
    (define m2 (multiplicand expr))
    (define dm1 (derive m1 var))
    (define dm2 (derive m2 var))
    (make-sum (make-product dm1 m2) (make-product m1 dm2))
)

; Exponentiations are represented as lists that start with ^.
(define (make-exp base exponent)
    (cond
        ((number? base) (expt base exponent))
        ((= exponent 0) 1)
        ((= exponent 1) base)
        (else (list '^ base exponent))
    )
)

(define (base e)
    (cadr e)
)

(define (exponent e)
    (caddr e)
)

(define (exp? e)
    (and (list? e) (eq? (car e) '^))
)

(define x^2 (make-exp 'x 2))
(define x^3 (make-exp 'x 3))

(define (derive-exp e var)
    (define a (base e))
    (define b (exponent e))
    (make-product (make-product b (make-exp a (- b 1))) (derive a var))
    ;(make-product b (make-exp a (- b 1)))
)
