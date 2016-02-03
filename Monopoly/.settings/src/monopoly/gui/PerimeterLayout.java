package monopoly.gui;

import java.awt.*;

/**
 * A custom layout manager that lays out components around the perimeter of a
 * rectangle. Components are arranged in order, proceeding clockwise.
 * 
 * @author Byron Weber Becker
 */
public class PerimeterLayout implements LayoutManager2 {
  private int rows;
  private int cols;
  private int originX;
  private int originY;
  private Component centerComp = null;

  /**
   * To put a component in the center of the layout:
   * <tt>this.add(component, PerimeterLayout.CENTER);</tt>.
   */
  public static final Object CENTER = new Object();

  /**
   * To put a component on the perimeter of the layout
   * <tt>this.add(component, PerimeterLayout.PERIMETER);</tt>.
   */
  public static final Object PERIMETER = new Object();

  /**
   * Lay the components out in a clockwise directions starting at the upper left
   * corner.
   * 
   * @param numRows
   *          the number of rows of components
   * @param numCols
   *          the number of columns of components
   */
  public PerimeterLayout(int numRows, int numCols) {
    this(numRows, numCols, 0, 0);
  }

  /**
   * Lay the components out in a clockwise direction starting at the given
   * origin.
   * 
   * @param numRows
   *          the number of rows of components
   * @param numCols
   *          the number of columns of components
   * @param xOrigin
   *          the x coordinate of the cell where layout should begin
   * @param yOrigin
   *          the y coordinate of the cell where layout should begin
   */
  public PerimeterLayout(int numRows, int numCols, int xOrigin, int yOrigin) {
    super();
    this.rows = numRows;
    this.cols = numCols;
    this.originX = xOrigin;
    this.originY = yOrigin;
  }

  /**
   * Adds the specified component with the specified name to the layout. Empty
   * implementation.
   * 
   * @param name
   *          the component name
   * @param comp
   *          the component to be added
   */
  public void addLayoutComponent(String name, Component comp) {
  }

  /**
   * Removes the specified component from the layout. Empty implementation.
   * 
   * @param comp
   *          the component to be removed
   */
  public void removeLayoutComponent(Component comp) {
  }

  /**
   * Calculates the preferred size dimensions for the specified panel given the
   * components in the specified parent container.
   * 
   * @param target
   *          the component to be laid out
   * @return the preferred size as a Dimension object
   * @see #minimumLayoutSize
   */
  public Dimension preferredLayoutSize(Container target) {
    synchronized (target.getTreeLock()) {
      Dimension dim = new Dimension(0, 0);
      int nmembers = target.getComponentCount();

      for (int i = 0; i < nmembers; i++) {
        Component m = target.getComponent(i);
        if (m.isVisible() && m != this.centerComp) {
          Dimension d = m.getPreferredSize();
          dim.height = Math.max(dim.height, d.height);
          dim.width = Math.max(dim.width, d.width);
        }
      }
      Insets insets = target.getInsets();
      dim.width = insets.left + insets.right + dim.width * this.cols;
      dim.height = insets.top + insets.bottom + dim.height * this.rows;
      return dim;
    }
  }

  /**
   * Calculates the minimum size dimensions for the specified panel given the
   * components in the specified parent container.
   * 
   * @param target
   *          the component to be laid out
   * @return the minimum size as a Dimension object
   * @see #preferredLayoutSize
   */
  public Dimension minimumLayoutSize(Container target) {
    synchronized (target.getTreeLock()) {
      Dimension dim = new Dimension(0, 0);
      int nmembers = target.getComponentCount();

      for (int i = 0; i < nmembers; i++) {
        Component m = target.getComponent(i);
        if (m.isVisible() && m != this.centerComp) {
          Dimension d = m.getMinimumSize();
          dim.height = Math.max(dim.height, d.height);
          dim.width = Math.max(dim.width, d.width);
        }
      }
      Insets insets = target.getInsets();
      dim.width = insets.left + insets.right + dim.width * this.cols;
      dim.height = insets.top + insets.bottom + dim.height * this.rows;
      return dim;
    }
  }

  /**
   * Lays out the container in the specified panel.
   * 
   * @param target
   *          the component which needs to be laid out
   */
  public void layoutContainer(Container target) {
    synchronized (target.getTreeLock()) {
      Insets insets = target.getInsets();
      int cellWidth = (target.getWidth() - (insets.left + insets.right))
          / this.cols;
      int cellHeight = (target.getHeight() - (insets.left + insets.right))
          / this.rows;

      int nmembers = target.getComponentCount();

      int x = this.originX;
      int y = this.originY;
      for (int i = 0; i < nmembers; i++) {
        Component m = target.getComponent(i);

        if (m == this.centerComp) { // position the center component
          int width = this.cols - 2;
          int height = this.rows - 2;
          m.setLocation(cellWidth, cellHeight);
          m.setSize(cellWidth * width, cellHeight * height);
        } else { // position components on the perimeter
          m.setSize(cellWidth, cellHeight);
          m.setLocation(x * cellWidth, y * cellHeight);
          if (y == 0 && x < this.cols) { // proceed across the top
            x++;
            if (x == this.cols) {
              x--;
              y++;
            }
          } else if (y > 0 && y < this.rows && x == this.cols - 1) { // proceed
                                                                     // down
                                                                     // right
                                                                     // edge
            y++;
            if (y == this.rows) {
              y--;
              x--;
            }
          } else if (y > 0 && x >= 0 && x < this.cols - 1) { // proceed back
                                                             // across the
                                                             // bottom
            x--;
            if (x < 0) {
              x = 0;
              y--;
            }
          } else {
            y--;
          }
        }
      }
    }
  }

  @Override
  public void addLayoutComponent(Component comp, Object constraints) {
    if (constraints == CENTER) {
      this.centerComp = comp;
    }
  }

  @Override
  public float getLayoutAlignmentX(Container target) {
    return 0.5F;
  }

  @Override
  public float getLayoutAlignmentY(Container target) {
    return 0.5F;
  }

  @Override
  public void invalidateLayout(Container target) {
  }

  @Override
  public Dimension maximumLayoutSize(Container target) {
    return new Dimension(1000, 1000); // bogus!
  }

}
