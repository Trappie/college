(define (caar x) (car (car x)))
(define (cadr x) (car (cdr x)))
(define (cdar x) (cdr (car x)))
(define (cddr x) (cdr (cdr x)))

(define (new_item value)
    (cons value nil)
)

; Some utility functions that you may find useful to implement.

(define (cons-all first rests)
  'replace-this-line)

(define (zip pairs)
  'replace-this-line)

;; Problem 17
;; Returns a list of two-element lists
(define (enumerate s)
  ; BEGIN PROBLEM 17
  (define (enum_rec s new_list index)
    (if (equal? s nil)
        new_list
        (enum_rec (cdr s) (append new_list (cons (cons index (cons (car s) nil)) nil)) (+ index 1))
        ;(
        ;    (define new_list (append new_list (cons (cons index (car s)) nil)))
        ;    (enum_rec (cdr s) new_list (+ index 1))
        ;)
    )
  )
  (if (list? s)
    (enum_rec s () 0)
  )
)
  ; END PROBLEM 17

;; Problem 18
;; List all ways to make change for TOTAL with DENOMS
(define (cons-all num s)
    (define (add-front lst)
        (cons num lst)
    )
    (if (equal? s ())
        (cons (cons num nil) nil)
        (map add-front s)
    )
)

; find the biggest-smaller in th denoms, which is a list in which the first one smaller than or equal to total
(define (biggest-smaller total denoms)
    (if (< total (car denoms))
        (biggest-smaller total (cdr denoms))
        denoms
    )
)


(define (list-change total denoms)
    (define (help target coins)
        (define coins (biggest-smaller target coins))
        (define part2 (list-change target (cdr coins))) ; without biggest
        (define part1 (cons-all (car coins) (list-change (- total (car coins)) denoms))) ; with biggest, 
        (if (equal? part2 ())
            part1
            (append part1 part2)
        )
    )
  ; BEGIN PROBLEM 18
    (cond
        ((= total 0) ())
        ((equal? denoms ()) ())
        (else (help total denoms))
    )
)
  ; END PROBLEM 18

;; return the length of the given scheme list
(define (len s)
    (if (equal? s nil)
        0
        (+ 1 (len (cdr s)))
    )
)

;; append an item to a scheme list properly
(define (append_proper s item)
    (append s (cons item nil))
)

;; zip the given double formed list in another direction
;; this zip might be different from zip in python
;; the double_list passed in will only have form like ((a b) (c d) (e f) ...)
;; and the result should be ((a c e ...) (b d f ...))

(define (zip double_list)
    (define (combine)
        (define following (zip (cdr double_list))) ; ((3 5) (4 6))
        (define first_pair (car double_list)); (1 2)
        (define list1 (cons (car first_pair) (car following)))
        (define list2 (cons (cadr first_pair) (cadr following)))
        (list list1 list2)
    )
    (if (equal? double_list ())
        (list () ())
        (combine)
    )
)
;; Problem 19
;; Returns a function that checks if an expression is the special form FORM
(define (check-special form)
  (lambda (expr) (equal? form (car expr))))

;; use these four procedures to check the special form of given expressions
(define lambda? (check-special 'lambda))
(define define? (check-special 'define))
(define quoted? (check-special 'quote))
(define let?    (check-special 'let))

;; Converts all let special forms in EXPR into equivalent forms using lambda
(define (let-to-lambda expr)
    ;; create lambda expression from given value and body of a "let" expression
    (define (create-lambda-expression value body)
        (define params (car (zip value)))
        (define arguments (apply-to-list (cadr (zip value)))); arguments of the let expression could be let expression, so convert them too
        (define lambda-exp (list 'lambda params (let-to-lambda body))); convert every expression in the body recursively
        (cons lambda-exp arguments)
    )

    ;; apply let-to-lambda to every item in a list and return the new list
    (define (apply-to-list lst)
        (if (equal? lst ())
            ()
            (cons (let-to-lambda (car lst)) (apply-to-list (cdr lst)))
        )
    )

  (cond ((atom? expr)
         ; BEGIN PROBLEM 19
         expr ; if the expr is atom like symbol or number, just return it 
         ; END PROBLEM 19
         )
        ((quoted? expr)
         ; BEGIN PROBLEM 19
         expr ; if the expr is quoted, return it
         ; END PROBLEM 19
         )
        ((or (lambda? expr)
             (define? expr))
         (let ((form   (car expr))
               (params (cadr expr))
               (body   (cddr expr)))
           ; BEGIN PROBLEM 19
           (append (list form params) (apply-to-list body))
           ;(list form params (let-to-lambda (car body)))
           ; END PROBLEM 19
           )) ; if the expression is lambda or define, convert all expressions in the body recursively and return it
        ((let? expr)
         (let ((values (cadr expr))
               (body   (cddr expr)))
           ; BEGIN PROBLEM 19
           (create-lambda-expression values (car body)); if the expression is let, call the method defined before to handle the converting
           ; END PROBLEM 19
           ))
        (else
         ; BEGIN PROBLEM 19
         (apply-to-list expr); if the expression is normal, convert every expression in the body recursively
         ; END PROBLEM 19
         )))
