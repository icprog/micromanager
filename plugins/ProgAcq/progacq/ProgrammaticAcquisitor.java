package progacq;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import mmcorej.CMMCore;
import mmcorej.DeviceType;

import org.micromanager.MMStudioMainFrame;
import org.micromanager.api.MMPlugin;
import org.micromanager.api.ScriptInterface;

// Big epic TODO to get my attention: You should really make this implement
// ActionListener!
public class ProgrammaticAcquisitor implements MMPlugin {
	public static String menuName = "Programmatic Acquisitor";
	public static String tooltipDescription = "Allows the acquiring of complex series of images.";

	private ScriptInterface app;
	private CMMCore core;
	private MMStudioMainFrame gui;

	private JFrame frame;
	private JTable stepsTbl;

	@Override
	public void dispose() {
		if (frame == null)
			return;

		frame.dispose();
		frame = null;
	};

	@Override
	public void setApp(ScriptInterface app) {
		this.app = app;
		this.core = app.getMMCore();
		this.gui = MMStudioMainFrame.getInstance(); // Removeme if unneeded!
	};

	@Override
	public void show() {
		if (frame == null)
			buildFrame();

		frame.setVisible(true);
	};

	@Override
	public void configurationChanged() {
		// TODO:
		// Our table is broken! We'll have to inform the user and drop any
		// columns of the table that aren't valid any more.
	};

	@Override
	public String getDescription() {
		return "Users can specify a sequence or range of settings on any number of devices, order them, and acquire all described images.";
	};

	@Override
	public String getVersion() {
		return "0.01";
	};

	@Override
	public String getInfo() {
		return "None yet!";
	};

	@Override
	public String getCopyright() {
		return "I have rights?";
	};

	private void buildFrame() {
		if (frame != null)
			return;

		frame = new JFrame(ProgrammaticAcquisitor.menuName);
		frame.getContentPane().setLayout(
				new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));

		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.LINE_AXIS));
		top.setBorder(BorderFactory.createTitledBorder("Steps"));

		stepsTbl = new JTable();
		stepsTbl.setFillsViewportHeight(true);
		stepsTbl.setAutoCreateColumnsFromModel(true);
		stepsTbl.setModel(new StepTableModel());

		JScrollPane tblScroller = new JScrollPane(stepsTbl);
		top.add(tblScroller);

		JPanel stepsBtns = new JPanel();
		stepsBtns.setLayout(new BoxLayout(stepsBtns, BoxLayout.PAGE_AXIS));
		stepsBtns.setAlignmentY(Component.TOP_ALIGNMENT);

		JButton selDevs = new JButton("Select Devices...");
		selDevs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SelectStringsDialog.doInstance(frame, getUnusedDevs(),
						getUsedDevs(), new WindowAdapter() {
							@Override
							public void windowClosed(WindowEvent e) {
								((StepTableModel) (stepsTbl.getModel()))
										.setColumns(SelectStringsDialog
												.getFinalList());
							}
						});
			}
		});

		JButton addRanges = new JButton("Add Ranges...");
		addRanges.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Vector<String> devs = getUsedDevs();
				if (devs.size() > 0) {
					AddStepsDialog.doInstance(frame, core, getUsedDevs(),
							new WindowAdapter() {
								@Override
								public void windowClosed(WindowEvent e) {
									insertRowsByRanges(AddStepsDialog
											.getResults());
								};
							});
				} else {
					JOptionPane.showMessageDialog(frame,
							"You must select at least one device.");
				}
			}
		});
		JButton addDisc = new JButton("Add Discretes...");
		JButton remStep = new JButton("Remove Steps");
		remStep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((StepTableModel) stepsTbl.getModel()).removeRows(stepsTbl
						.getSelectedRows());
			}
		});

		stepsBtns.add(selDevs);
		stepsBtns.add(addRanges);
		stepsBtns.add(addDisc);
		stepsBtns.add(remStep);

		top.add(stepsBtns);

		frame.getContentPane().add(top);

		JPanel bottom = new JPanel();
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.LINE_AXIS));

		JPanel timeBox = new JPanel();
		timeBox.setLayout(new BoxLayout(timeBox, BoxLayout.LINE_AXIS));
		timeBox.setBorder(BorderFactory.createTitledBorder("Time"));

		final JCheckBox timeCB = new JCheckBox("");

		JLabel step = new JLabel("Interval:");
		// TODO: Enforce a minimum? The stage needs time to move.
		step.setToolTipText("Delay between acquisition sequences in milliseconds. Be careful not to set this too short!");
		final JTextField stepBox = new JTextField(8);
		stepBox.setMaximumSize(stepBox.getPreferredSize());

		JLabel count = new JLabel("Count:");
		count.setToolTipText("Number of acquisition sequences to perform. Each is described by the table above.");
		final JTextField countBox = new JTextField(8);
		countBox.setMaximumSize(countBox.getPreferredSize());

		timeCB.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				stepBox.setEnabled(timeCB.getModel().isSelected());
				countBox.setEnabled(timeCB.getModel().isSelected());
			}
		});

		timeBox.add(timeCB);
		timeBox.add(step);
		timeBox.add(stepBox);
		timeBox.add(count);
		timeBox.add(countBox);

		bottom.add(timeBox);

		JButton go = new JButton("Start");

		bottom.add(go);

		frame.getContentPane().add(bottom);

		frame.pack();
	};

	private Vector<Vector<Double>> getRows(List<double[]> subranges) {
		double[] first = (double[]) subranges.get(0);
		Vector<Vector<Double>> rows = new Vector<Vector<Double>>();

		if (subranges.size() == 1) {
			for (double val : first) {
				Vector<Double> row = new Vector<Double>();
				row.add(val);
				rows.add(row);
			}
		} else {
			for (double val : first) {
				Vector<Vector<Double>> subrows = getRows(subranges.subList(1,
						subranges.size()));

				for (Vector<Double> row : subrows) {
					Vector<Double> newRow = new Vector<Double>(row);
					newRow.add(0, val);
					rows.add(newRow);
				}
			}
		}

		return rows;
	}

	private void insertRowsByRanges(Vector<double[]> ranges) {
		// Each element of range is a triplet of min/step/max.
		// This function determines the discrete values of each range, then
		// works out all possible values and adds them as rows to the table.
		Vector<double[]> values = new Vector<double[]>(ranges.size());

		for (double[] triplet : ranges) {
			double[] discretes = new double[(int) ((triplet[2] - triplet[0]) / triplet[1])];

			for (int i = 0; i < discretes.length; ++i)
				discretes[i] = triplet[0] + triplet[1] * i;

			values.add(discretes);
		}

		// Build a quick list of indices of X/Y stage devices.
		// Below, we condense the X and Y coordinates into an ordered pair so
		// they can be inserted into the table. This list is used to determine
		// which sets of indices need to be squished into a single value.
		Vector<Integer> xyStages = new Vector<Integer>(stepsTbl.getModel()
				.getColumnCount());
		for (int i = 0; i < stepsTbl.getModel().getColumnCount(); ++i) {
			try {
				if (core.getDeviceType(stepsTbl.getModel().getColumnName(i))
						.equals(DeviceType.XYStageDevice))
					xyStages.add(i);
			} catch (Exception e) {
				// I can't think of a more graceless way to resolve this issue.
				// But then, nor can I think of a more graceful one.
				throw new Error("Couldn't resolve type of device \""
						+ stepsTbl.getModel().getColumnName(i) + "\"", e);
			}
		}

		Vector<Vector<Double>> rows = getRows(values);

		for (Vector<Double> row : rows) {
			if (xyStages.size() > 0) {
				Vector<String> finalRow = new Vector<String>();

				for (int i = 0; i < row.size(); ++i)
					if (xyStages.contains(i))
						finalRow.add(row.get(i) + ", " + row.get(++i));
					else
						finalRow.add("" + row.get(i));

				((StepTableModel) stepsTbl.getModel()).insertRow(finalRow
						.toArray());
			} else {
				((StepTableModel) stepsTbl.getModel()).insertRow(row.toArray());
			}
		}
	};

	/**
	 * This function runs a generalized acquisition sequence. It's a mixture of
	 * Micro-Manager's built in sequencing support (confusing) and waiting for
	 * motors.
	 * 
	 * @param core
	 *            The Micro-Manager core reference to work with.
	 * @param sequences
	 *            A list of states for all devices. Each 'row' should have the
	 *            same length, and the first 'row' should be a list of device
	 *            names (columns).
	 * @param waitEach
	 *            If true, waits for each device in turn rather than moving them
	 *            simultaneously.
	 * @param steps
	 *            Number of acquisition sequences to run.
	 * @param timestep
	 *            Delay in milliseconds between the beginning of each
	 *            acquisition. Arbitrary if only a single acquisition.
	 * @throws Exception
	 *             on encountering malformed data or bad device names, or an
	 *             exception while stepping (i.e. motor malfunction).
	 */
	public static void performAcquisition(CMMCore core,
			Vector<Vector<String>> sequences, boolean waitEach, int steps,
			double timestep) throws Exception {
		Vector<String> devices = sequences.remove(0);

		core.removeImageSynchroAll();
		for (String dev : devices)
			core.assignImageSynchro(dev);

		for (int seq = 0; seq < steps; ++seq) {
			int step = 0;
			for (Vector<String> positions : sequences) {
				for (int i = 0; i < devices.size(); ++i) {
					String dev = devices.get(i);
					String pos = positions.get(i);
					try {
						if (core.getDeviceType(dev).equals(
								DeviceType.StageDevice))
							core.setPosition(dev, Double.parseDouble(pos));
						else if (core.getDeviceType(dev).equals(
								DeviceType.XYStageDevice))
							core.setXYPosition(dev, parseX(pos), parseY(pos));
						else
							throw new Exception("Unknown device type for \""
									+ dev + "\"");
					} catch (NumberFormatException e) {
						throw new Exception("Malformed number for device \""
								+ dev + "\", row " + step, e);
					}

					if (waitEach)
						core.waitForDevice(dev);
				}

				if (!waitEach)
					for (String dev : devices)
						core.waitForDevice(dev);

				core.snapImage();

				++step;
			}
		}
	};

	private static double parseX(String pair) {
		return Double.parseDouble(pair.substring(0, pair.indexOf(' ')));
	};

	private static double parseY(String pair) {
		return Double.parseDouble(pair.substring(pair.indexOf(' ') + 1));
	}

	private Vector<String> getUnusedDevs() {
		Vector<String> all = new Vector<String>((int) core.getLoadedDevices()
				.size());

		for (String entry : core.getLoadedDevices())
			all.add(entry);

		for (int i = 0; i < stepsTbl.getModel().getColumnCount(); ++i)
			all.remove(stepsTbl.getModel().getColumnName(i));

		return all;
	};

	private Vector<String> getUsedDevs() {
		Vector<String> res = new Vector<String>();

		for (int i = 0; i < stepsTbl.getModel().getColumnCount(); ++i)
			res.add(stepsTbl.getModel().getColumnName(i));

		return res;
	};
};