const fs = require('fs');

const file = fs.readFileSync('Operations.txt').toString().split('\r\n');
const set = new Set();
let operation;
let number;

for (line of file) {
    operation = line.slice(0, 1);
    number = line.slice(1);

    switch (operation) {
        case 'a':
            set.add(Number(number));
            break;
        case 'r':
            set.delete(Number(number));
            break;
        case 'f':
            break;
        default:
            break;
    }
}

const arr = Array.from(set);
arr.sort(function(a, b){return a-b});
console.log(arr);

let str = '';
for (element of arr) {
    str += element + ', ';
    console.log(typeof element);
}
str = str.slice(0, str.length - 2);

console.log('Size: ' + arr.length);
console.log(str);
// for (number )

// console.log(set);