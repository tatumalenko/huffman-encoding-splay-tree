# Huffman Encoding and Splay Tree Implementation

This repo contains the code for my COMP 352 (Data Structures and Algorithms) project. It includes an implementation of Huffman Encoding and Splay Tree. The document `A3Instructions.pdf` contains the requirements set out for the implementations.

## Getting Started
To get this repo, simply clone it:
```
git clone https://github.com/tatumalenko/huffman-encoding-splay-tree.git
```

This project is built using [Bazel](https://bazel.build/). To build the entire repo, simply use the following command in the root:
```
bazel build ...
```

Once the build is completed, you can then run either of the implemented algorithms as indicated in the following instructions.

### Huffman Encoding:
```
bazel-bin/Huffman <text-file>
```
where `<text-file>` is the file name you wish to use to process the encoding scheme (build the frequency table).

This will open up a console input where you can specify the string you wish to encode. After pressing the Enter key, the code will display the resulting Huffman encoding of your input. See `A3Instructions.pdf` for more details.

Example:
```
bazel-bin/Huffman Jabberwock.txt
poop

010101000110011001010100
```

### Splay Tree:
```
bazel-bin/SplayTree <text-file> <step-to-traverse>
```
where:  
`<text-file>` is the file name you wish to use that contains the newline delimited set of operations to process on the Splay Tree.
`<step-to-traverse>` is the number of operations that the algorithm will output a post-order traversal 

This will display information about the resulting tree including a post-order traversal of the tree, the number of comparisons, zig-zag, and zig-zig operations. See `A3Instructions.pdf` for more details.

Example:
```
bazel-bin/SplayTree Operations.txt 40

Traversal at 40: 82,84,280,304,349,261,126,111,529,563,500,900,794,947,703,672,470,456
81 compares
8 Zig-Zigs
14 Zig-Zags
```
