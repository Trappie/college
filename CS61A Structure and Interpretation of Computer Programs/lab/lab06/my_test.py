from car import *
class FoodTruck(MonsterTruck):
   delicious = 'meh'
   def serve(self):
       if FoodTruck.size == 'delicious':
           print('Yum!')
       if self.food != 'Tacos':
           return 'But no tacos...'
       else:
           return 'Mmm!'
taco_truck = FoodTruck('Tacos', 'Truck')
#taco_truck.food = 'Guacamole'
#taco_truck.serve()
#
#taco_truck.food = taco_truck.make
#FoodTruck.size = taco_truck.delicious
#taco_truck.serve()
#
#taco_truck.size = 'delicious'
#taco_truck.serve()


taco_truck.drive()
mon_truck = MonsterTruck('Toyota', 'Camry')
mon_truck.drive()
