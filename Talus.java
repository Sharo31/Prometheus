package com.sharo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.HTTPServer;

import javax.swing.*;
import java.lang.*;

 class Talus2 extends Talus {
	JFrame f;
	JRadioButton control;
	JRadioButton j2;
	JRadioButton j3;
	JLabel l1,l2,l3;
	//public static boolean running=true;
	
	    Talus2( final Thread t1){
		 f=new JFrame();
		 f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setLayout(null);
		 l1=new JLabel("Welcome to LAM RESEARCH");
		l1.setBounds(50, 25, 200, 30);
		l2= new JLabel("Click on the button to start scraping data from the system");
		l2.setBounds(10, 75, 350, 30);
		
		final JButton submit =new JButton("Submit");
		submit.setBounds(75,150,100,30);
	    final JButton close =new JButton("Close");
		close.setBounds(75,200,100,30);
		
		f.add(l1);
		f.add(l2);
		
		f.add(submit);
		f.add(close);
		f.setSize(400,300);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		
		f.addWindowListener(new WindowAdapter() {

	        @Override
	        public void windowClosing(WindowEvent e) {
	            running = false;
	        }

	        @Override
	        public void windowClosed(WindowEvent e) {
	        	running=false;
	           System.exit(0);
	        }
	});
		
				
		close.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				//System.exit(0);
				running=false;
				//t2.stop();
			
			}
		});	
			
		submit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				
			
			JOptionPane.showMessageDialog(f, "Starting Data Scraping", null, 0,null);
			running=true;
			try {
			t1.start();}
			catch(java.lang.IllegalThreadStateException exception) {
				exception.printStackTrace();
			}
			
			
		
		 }});
			

           
	}
	
	
	

}
public class Talus  implements Runnable{
	

	

	public static boolean running=true;
	static final Gauge tm = Gauge.build().name("total_memory").help("blah").register();
    static final Gauge fm = Gauge.build().name("free_memory").help("blah").register();
    static final Gauge um = Gauge.build().name("used_memory").help("blah").register();
    static final Gauge mm = Gauge.build().name("max_memory").help("blah").register();
   
    static final Gauge tds = Gauge.build().name("total_disc_space").help("blah").register();
    static final Gauge fds = Gauge.build().name("free_disc_space").help("blah").register();
    static final Gauge uds = Gauge.build().name("used_disc_space").help("blah").register();
    static final Gauge la = Gauge.build().name("system_load_average").help("blah").register();
    static final Gauge ram = Gauge.build().name("total_ram").help("blah").register();
    static final Gauge fss = Gauge.build().name("free_swap_size").help("blah").register();
    static final Gauge pcl = Gauge.build().name("proces_cpu_load").help("blah").register();
    static final Gauge scl = Gauge.build().name("system_cpu_load").help("blah").register();
    static final Gauge pct = Gauge.build().name("process_cpu_time").help("blah").register();
    static final Gauge cut = Gauge.build().name("cpu_uptime").help("blah").register();
    static final Gauge tss = Gauge.build().name("total_swap_size").help("blah").register();
    static final Gauge core = Gauge.build().name("total_cpu_core").help("blah").register();

	public void run() {
		// TODO Auto-generated method stub
		while(running) {
			  int mb = 1024 * 1024; 
		       
				// get Runtime instance
				Runtime instance = Runtime.getRuntime();

				System.out.println("***** Heap utilization statistics [MB] *****\n");

				// available memory
				System.out.println("Total Memory: " + instance.totalMemory() / mb);
				tm.set(instance.totalMemory() / mb);

				// free memory
				System.out.println("Free Memory: " + instance.freeMemory() / mb);
				fm.set(instance.freeMemory() / mb);

				// used memory
				System.out.println("Used Memory: "
						+ (instance.totalMemory() - instance.freeMemory()) / mb);
				um.set((instance.totalMemory() - instance.freeMemory()) / mb);

				// Maximum available memory
				System.out.println("Max Memory: " + instance.maxMemory() / mb);
				mm.set(instance.maxMemory() / mb);
				
				File cDrive = new File("C:");
				System.out.println(String.format("Total space: %.2f GB",
				  (double)cDrive.getTotalSpace() /1073741824));tds.set((double)cDrive.getTotalSpace() /1073741824);
				System.out.println(String.format("Free space: %.2f GB", 
				  (double)cDrive.getFreeSpace() /1073741824));fds.set((double)cDrive.getFreeSpace() /1073741824);
				System.out.println(String.format("Usable space: %.2f GB", 
				  (double)cDrive.getUsableSpace() /1073741824));uds.set((double)cDrive.getUsableSpace() /1073741824);
				
				
				/*MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
				System.out.println(String.format("Initial memory: %.2f GB", 
				  (double)memoryMXBean.getHeapMemoryUsage().getInit() /1073741824));
				System.out.println(String.format("Used heap memory: %.2f GB", 
				  (double)memoryMXBean.getHeapMemoryUsage().getUsed() /1073741824));
				System.out.println(String.format("Max heap memory: %.2f GB", 
				  (double)memoryMXBean.getHeapMemoryUsage().getMax() /1073741824));
				System.out.println(String.format("Committed memory: %.2f GB", 
				  (double)memoryMXBean.getHeapMemoryUsage().getCommitted() /1073741824));*/
				
				long memorySize = ((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
				ram.set((double) memorySize);
				long freeswapsize=((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getFreeSwapSpaceSize();
				fss.set((double) freeswapsize);
				long totalswapsize=((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalSwapSpaceSize();
				tss.set((double) totalswapsize);
				double cpuloadprocess=((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getProcessCpuLoad();
				pcl.set((double) cpuloadprocess);
				double cpuloadsys=((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getSystemCpuLoad();
				scl.set((double) cpuloadsys);
				long cpuTime=((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getProcessCpuTime();
				pct.set((double) cpuTime);
				long uptime=ManagementFactory.getRuntimeMXBean().getUptime();
				cut.set((double) uptime);
				int processors = Runtime.getRuntime().availableProcessors();
				core.set((double)processors );
				
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				
				 
		 }
		
	}
	public static void main(String args[]) throws IOException {
		new HTTPServer(1235);
		
		
		Talus thread1=new Talus();
		Thread t1=new Thread(thread1);
		new Talus2(t1);
		
			
	}
	
}

