# Train problem 

# matrix = [
#     [4, 100, 200, 140, 600],
#     [50, 60, 70, 100, 50, 41, 25, 78, 90, 41, 25, 78, 90, 41, 600]]

# carts = input()
# packages = input()

with open('train_input.txt', 'r') as file:
    lines = file.readlines()
    carts = lines[0].split()
    packages = lines[1].split()
    # for line in lines:
    #     print(line.strip())

total_carts = int(carts[0])
carts = carts[1:total_carts +1]
packages = [int(s) for s in packages]
cart_tonnage =[int(s) for s in carts] 
packages.sort(reverse=True)
cart_tonnage.sort(reverse=True)


added_weight = []
cart_weight = []
#print(packages)

used_packages = []

output = []
for i in range(total_carts):
    curr_weight = 0
    temp = ""
    temp += f"{cart_tonnage[i]} -> ("

    for j in range(len(packages)):

        if packages[j] in used_packages:        #package = int(package)
            print("", end="")
        else:
            if curr_weight + packages[j] <= cart_tonnage[i]:
                temp += f"{packages[j]}, "
                curr_weight += packages[j]
                used_packages.append(packages[j])
    
    if curr_weight > 0:
        temp += "\b\b"
    temp += f") = {curr_weight}"
    # print(used_packages)
    output.append(temp)
    
printed = set()

# print(carts)
for cart in carts:
    for string in output:
        if cart+" ->" in string and string not in printed:
            print(string)
            printed.add(string)


            
    # cart_weight.append(added_weight)
    # cart_weight.append(curr_weight)
    
# print(cart_weight)
