/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.mahout.math;

import java.util.Iterator;

import org.junit.Test;

public final class TestSparseMatrix extends MatrixTest {

  @Override
  public Matrix matrixFactory(double[][] values) {
    Matrix matrix = new SparseMatrix(values.length, values[0].length);
    for (int row = 0; row < matrix.rowSize(); row++) {
      for (int col = 0; col < matrix.columnSize(); col++) {
        matrix.setQuick(row, col, values[row][col]);
      }
    }
    return matrix;
  }

  /** Test copy method of sparse matrices which have empty non-initialized rows */
  @Test
  public void testSparseCopy() {
    SparseMatrix matrix = createSparseMatrixWithEmptyRow();
    Matrix copy = matrix.clone();

    assertSame("wrong class", copy.getClass(), matrix.getClass());

    SparseMatrix castedCopy = (SparseMatrix) copy;
        
    Iterator<MatrixSlice> originalSlices = matrix.iterator();
    Iterator<MatrixSlice> copySlices = castedCopy.iterator();
    
    while (originalSlices.hasNext() && copySlices.hasNext()) {
      MatrixSlice originalSlice = originalSlices.next();
      MatrixSlice copySlice = copySlices.next();
      assertEquals("Wrong row indices.", originalSlice.index(), copySlice.index());
      assertEquals("Slices are not equal.", originalSlice, copySlice);
    }
    
    assertSame("Number of rows of original and copy are not equal.", originalSlices.hasNext(), copySlices.hasNext());
  }
  
  /**
   * @return Sparse matrix whose last row is empty. Since no entry is set in the last row, there is no vector instance
   * initialized in the underlying map.
   */
  private SparseMatrix createSparseMatrixWithEmptyRow() {
    SparseMatrix result = new SparseMatrix(3, 3);
    result.setQuick(0, 0, 1);
    result.setQuick(1, 1 ,1);
    result.setQuick(1, 2, 1);
    return result;
  }
}
