import argparse

parser = argparse.ArgumentParser(description='Convert grayscale pixels into black/white ones according with given threshold value')
parser.add_argument('--in', help='Input file')
parser.add_argument('--out', help='Output file')
parser.add_argument('--threshold', help='All pixels below this threshold will be set to 0')
parser.add_argument('--hasLabel', help='1 if has label, 0 otherwise')
args = vars(parser.parse_args())

file_in = open(args['in'])
file_out = open(args['out'], 'w')

if args['hasLabel'] == '0':
    hasLabel = False
else:
    hasLabel = True

threshold = int(args['threshold'])    
lines2 = []

counter = 0
for line in file_in.readlines():
    if line.startswith('#') or len(line.strip())==0:
        lines2.append(line)
    elif line.startswith('0') or line.startswith('1') or line.startswith('2') or line.startswith('3') or line.startswith('4') or line.startswith('5') or line.startswith('6') or line.startswith('7') or line.startswith('8') or line.startswith('9'):
        a = line.split(',')
        b = []
        for i in range(0,len(a)):            
            if i==0 and hasLabel == 1:
                b.append(a[i])
            else:
                c = int(a[i])                      
                if c < threshold:
                    c = 0
                else:
                    c = 1
                b.append(str(c))
        line2 = ','.join(b) + '\n'
        lines2.append(line2)
    counter = counter + 1
    if counter % 2000 == 0:
        print('read %d lines' % counter)

file_in.close()
        
print('Start writing file %s' % args['out'])        

counter = 0
for l in lines2:
    file_out.write(l)
    counter = counter + 1
    if counter % 2000 == 0:
        print('write %d lines' % counter)

file_out.close()
